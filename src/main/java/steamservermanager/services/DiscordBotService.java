package steamservermanager.services;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.JDABuilder;
import steamservermanager.discordbot.SteamServerManagerDiscordBot;
import steamservermanager.discordbot.helpers.DiscordBotSlashCommandHelper;
import steamservermanager.discordbot.listener.DiscordBotListener;
import steamservermanager.eao.DiscordBotEAO;
import steamservermanager.events.EventManagerService;
import steamservermanager.models.DiscordBot;
import steamservermanager.utils.ServiceProvider;

public class DiscordBotService {

	private JDA jda;
	private DiscordBotEAO discordBotEAO = ServiceProvider.provide(DiscordBotEAO.class);
	private EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);

	public void setDiscordBot(DiscordBot discordBot) {
		DiscordBot discordBotLoaded = discordBotEAO.find(1L);

		discordBot.setIdDiscordBot(1L);

		if (discordBotLoaded == null) {
			discordBotEAO.persist(discordBot);
		} else {
			discordBotEAO.merge(discordBot);
		}
	}

	public void start() {
		if (jda == null) {
			createJDAConnection();
		} else if (jda != null && !jda.getStatus().equals(Status.CONNECTED)) {
			stop();

			createJDAConnection();
		}
	}

	private void createJDAConnection() {
		DiscordBot discordBot = discordBotEAO.find(1L);

		DiscordBotListener discordBotListener = eventManager.getDiscordBotListener();

		try {
			if (discordBot != null) {
				jda = JDABuilder.createDefault(discordBot.getToken())
						.addEventListeners(new SteamServerManagerDiscordBot(eventManager.getDiscordBotListener()))
						.build();
			}

		} catch (Exception ex) {
			discordBotListener.onDiscordBotChangedStatus(ex.getMessage());
			discordBotListener.onDiscordBotStopped();
		}
	}

	public void updateSlashCommands() {
		if (jda != null && jda.getStatus().equals(Status.CONNECTED)) {
			DiscordBotSlashCommandHelper.updateCommands(jda);
		}
	}

	public Status getStatus() {
		if (jda != null) {
			return jda.getStatus();
		}

		return null;
	}

	public void stop() {

		if (jda != null) {
			jda.shutdownNow();
		}
	}
}
