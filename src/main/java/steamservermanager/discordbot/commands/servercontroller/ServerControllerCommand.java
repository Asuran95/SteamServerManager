package steamservermanager.discordbot.commands.servercontroller;

import java.util.List;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.ServerGame;
import steamservermanager.services.ServerRunnerService;
import steamservermanager.utils.ServiceProvider;

public class ServerControllerCommand extends DiscordCommandHandler {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);
	
	public ServerControllerCommand() {
		super(new ServerControllerCommandValidator());
	}
	
	@Override
	protected void action(SlashCommandInteractionEvent event) {

		String subcommandName = event.getSubcommandName();
		
		if (subcommandName.equals("start")) {
			
			long id = event.getOption("name").getAsLong();
			
			if (id == 1) {
				event.reply(event.getUser().getName() + " has initialized all servers.").queue();
				startAllServers(event);
				
				return;
			}
			
			event.reply("Server " + serverGameEAO.find(id).getName() + " (" + serverGameEAO.find(id).getGameName() + ")" + " has been initialized.").queue();
			startServerById(event, id);
			
		} else if (subcommandName.equals("stop")) {

			long id = event.getOption("name").getAsLong();
			
			if (id == 1) {
				event.reply(event.getUser().getName() + " has stopped all servers.").queue();
				stopAllServers(event);
				
				return;
			}
			
			event.reply("Server " + serverGameEAO.find(id).getName() + " (" + serverGameEAO.find(id).getGameName() + ")" + " has been stopped.").queue();
			stopServerById(event, id);
			
		}
	}
	
	private void startAllServers(SlashCommandInteractionEvent event) {
		List<ServerGame> serverList = ServiceProvider.provide(ServerGameEAO.class).findAll();
		
		for (ServerGame serverGame : serverList) {
			startServerById(event, serverGame.getIdServerGame());
		}
	}
	
	private void stopAllServers(SlashCommandInteractionEvent event) {
		List<ServerGame> serverList = ServiceProvider.provide(ServerGameEAO.class).findAll();
		
		for (ServerGame serverGame : serverList) {
			stopServerById(event, serverGame.getIdServerGame());
		}
	}
	
	private void startServerById(SlashCommandInteractionEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);
		
		if (serverGame == null) {
			throw new RuntimeException("There is no game server with this ID.");
		}
		
		startServerGame(event, serverGame);
	}
	
	private void stopServerById(SlashCommandInteractionEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);
		
		if (serverGame == null) {
			throw new RuntimeException("There is no game server with this ID.");
		}
		
		stopServerGame(event, serverGame);
	}

	private void startServerGame(SlashCommandInteractionEvent event, ServerGame serverGame) {
		ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);
		serverRunnerService.startServer(serverGame);
	}
	
	private void stopServerGame(SlashCommandInteractionEvent event, ServerGame serverGame) {
		ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);
		serverRunnerService.stopServer(serverGame);
	}
}
