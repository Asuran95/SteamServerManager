package steamservermanager.discordbot.commands.controlserver;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.ServerGame;
import steamservermanager.services.ServerRunnerService;
import steamservermanager.utils.ServiceProvider;

public class ControlServerCommand extends DiscordCommandHandler {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);
	
	public ControlServerCommand() {
		super(new ControlServerCommandValidator());
	}
	
	@Override
	protected void action(SlashCommandInteractionEvent event) {

		String subcommandName = event.getSubcommandName();
		
		if (subcommandName.equals("start")) {
			
			long id = event.getOption("name").getAsLong();
			startServerById(event, id);
			
			return;
			
		} else if (subcommandName.equals("stop")) {

			long id = event.getOption("name").getAsLong();
			stopServerById(event, id);
			
			return;
		}
		
	}
	
	private void startServerById(SlashCommandInteractionEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);
		
		if (serverGame == null) {
			throw new RuntimeException("There is no game server with this ID.");
		}
		
		startServerGame(event, serverGame);
		event.reply("Server #" + id + " - " + serverGame.getName() + " (" + serverGame.getGameName() + ")" + " has been initialized.").queue();
	}
	
	private void stopServerById(SlashCommandInteractionEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);
		
		if (serverGame == null) {
			throw new RuntimeException("There is no game server with this ID.");
		}
		
		stopServerGame(event, serverGame);
		event.reply("Server #" + id + " - " + serverGame.getName() + " (" + serverGame.getGameName() + ")" + " has been stopped.").queue();
	}

	private void startServerGame(SlashCommandInteractionEvent event, ServerGame serverGame) {
		ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);
		serverRunnerService.startServer(serverGame);
	}
	
	private void stopServerGame(SlashCommandInteractionEvent event, ServerGame serverGame) {
		ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);
		serverRunnerService.stopServer(serverGame);
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
