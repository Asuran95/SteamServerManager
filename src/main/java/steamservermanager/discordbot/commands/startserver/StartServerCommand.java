package steamservermanager.discordbot.commands.startserver;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.ServerGame;
import steamservermanager.services.ServerRunnerService;
import steamservermanager.utils.ServiceProvider;

public class StartServerCommand extends DiscordCommandHandler {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);
	
	public StartServerCommand() {
		super(new StartServerCommandValidator());
	}
	
	@Override
	protected void action(SlashCommandInteractionEvent event) {
		long id = event.getOption("id").getAsLong();
		
		if (id >= 1) {
			startServerById(event, id);
		}
	}
	
	private void startServerById(SlashCommandInteractionEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);
		
		if (serverGame == null) {
			//throw new RuntimeException("There is no game server with this ID.");
			event.reply("There is no game server with this ID.").setEphemeral(true).queue();
			return;
		}
		
		startServerGame(event, serverGame);
		event.reply("Server #" + id + " - " + serverGame.getName() + " (" + serverGame.getGameName() + ")" + " has been initialized.").queue();
	}
	
	private void startServerGame(SlashCommandInteractionEvent event, ServerGame serverGame) {
		ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);
		serverRunnerService.startServer(serverGame);
	}
	
	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
