package steamservermanager.services;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import steamservermanager.discordbot.SteamServerManagerDiscordBot;
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
				          .addEventListeners(new SteamServerManagerDiscordBot(discordBot.getPrefix(), eventManager.getDiscordBotListener()))
				          .build();
				restartSlashCommands();
			}			
		} catch (Exception ex) {
			discordBotListener.onDiscordBotChangedStatus(ex.getMessage());
			discordBotListener.onDiscordBotStopped();
		}

	}
	
	private void restartSlashCommands() {
		CommandListUpdateAction commands = jda.updateCommands();

		commands.addCommands(
			Commands.slash("serverlist", "Shows the list of available servers.")
		);
		
		commands.addCommands(
			Commands.slash("updateall", "Updates all listed servers.")
		);
		
		commands.addCommands(
			Commands.slash("updategame", "Updates all servers from a specific game.")
				.addOption(OptionType.INTEGER, "steamid", "SteamID of the game.", true)
		);
		
		commands.addCommands(
			Commands.slash("updateserver", "Updates a specific server.")
				.addOption(OptionType.INTEGER, "serverid", "ServerID from the list.", true)
		);
		
		commands.addCommands(
			Commands.slash("startserver", "Starts one of the available servers from the list.")
				.addOption(OptionType.INTEGER, "id", "The server's ID.", true)
		);
		
		commands.queue();
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
