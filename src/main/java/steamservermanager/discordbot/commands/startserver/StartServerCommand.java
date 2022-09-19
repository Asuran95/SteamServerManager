package steamservermanager.discordbot.commands.startserver;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
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
	protected void action(MessageReceivedEvent event, String[] commands) {
		
		Long id = getIdServerGameParameter(commands);
		
		if (id != null) {
			startServerById(event, id);
		} else {
			throw new RuntimeException("Invalid Command!");
		}
	}
	
	private void startServerById(MessageReceivedEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);
		
		if (serverGame == null) {
			throw new RuntimeException("There is no game server with this ID.");
		}
		
		startServerGame(event, serverGame);
	}
	
	private void startServerGame(MessageReceivedEvent event, ServerGame serverGame) {
		ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);
		serverRunnerService.startServer(serverGame);
	}
	
	private Long getIdServerGameParameter(String[] commands) {
		Long id = null;
		
		try {
			if (commands.length == 1) {
				String param = commands[0];
				id = Long.valueOf(param);
			}

		} catch (Exception ex) {
			throw new RuntimeException("Invalid Parameter!");
		}
		
		return id;
	}
	
	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
