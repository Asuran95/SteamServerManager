package steamservermanager.services;

import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import steamservermanager.discordbot.SteamServerManagerDiscordBot;
import steamservermanager.discordbot.listener.DiscordBotListener;
import steamservermanager.eao.DiscordBotEAO;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.events.EventManagerService;
import steamservermanager.models.DiscordBot;
import steamservermanager.models.ServerGame;
import steamservermanager.utils.ServiceProvider;

public class DiscordBotService {
	
	private JDA jda;
	private DiscordBotEAO discordBotEAO = ServiceProvider.provide(DiscordBotEAO.class);
	private EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);
	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);
	
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
		
		OptionData subcommandGameOption = 
				populateGameOption(new OptionData(OptionType.INTEGER, "name", "Game's name", true));
		OptionData subcommandServerOption = 
				populateServerOption(new OptionData(OptionType.INTEGER, "name", "Server's name", true));

		commands.addCommands(
			Commands.slash("serverlist", "Shows the list of available servers.")
		);
		
		commands.addCommands(
			Commands.slash("startserver", "Starts one of the available servers from the list.")
				.addOption(OptionType.INTEGER, "id", "The server's ID.", true)
		);
		
		commands.addCommands(Commands.slash("update", "Update all servers, all servers of a specific game or a specific server")
				.addSubcommands(new SubcommandData("all", "Updates every server from the list."))
				.addSubcommands(new SubcommandData("game", "Updates all games from it's Steam ID")
						.addOptions(subcommandGameOption)
				)
				.addSubcommands(new SubcommandData("server", "Updates a specific server")
						.addOptions(subcommandServerOption)
				)
		);
		
		commands.queue();
	}
	
	private OptionData populateGameOption(OptionData option) {
		List<ServerGame> serverList = serverGameEAO.findAll();
		List<Integer> ids = new ArrayList<Integer>();
		
		for (ServerGame serverGame : serverList) {
			
			if (!ids.contains(serverGame.getAppID())) {
				option.addChoice(serverGame.getGameName(), serverGame.getAppID());
			}
			
			ids.add(serverGame.getAppID());
			
		}
		
		return option;
	}
	
	private OptionData populateServerOption(OptionData option) {
		List<ServerGame> serverList = serverGameEAO.findAll();
		
		for (ServerGame serverGame : serverList) {
			option.addChoice(serverGame.getServerName(), serverGame.getIdServerGame());
		}
		
		return option;
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
