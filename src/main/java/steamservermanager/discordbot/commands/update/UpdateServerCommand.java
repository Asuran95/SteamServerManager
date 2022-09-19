package steamservermanager.discordbot.commands.update;

import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.ServerGame;
import steamservermanager.services.UpdaterServerGameService;
import steamservermanager.utils.ServiceProvider;

public class UpdateServerCommand extends DiscordCommandHandler {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);

	public UpdateServerCommand() {
		super(new UpdateServerCommandValidator());
	}

	@Override
	protected void action(MessageReceivedEvent event, String[] commands) {
		switch (commands[0]) {
			case "all":
				updateAllServers(event);
				break;
			case "steamid":
				Integer steamID = getSteamIdParameter(commands);;
				
				updateServersbySteamID(event, steamID);
				break;
			default:
				Long id = getIdServerGameParameter(commands);
				
				if (id != null) {
					updateServerById(event, id);
				} else {
					throw new RuntimeException("Invalid Command!");
				}	
		}
	}
	
	private void updateAllServers(MessageReceivedEvent event) {
		List<ServerGame> serverGameList = serverGameEAO.findAll();

		if (!serverGameList.isEmpty()) {
			for (ServerGame serverGame : serverGameList) {
				updateServerGame(event, serverGame);
			}
		} else {
			throw new RuntimeException("There is no game server installed.");
		}
	}
	
	private void updateServersbySteamID(MessageReceivedEvent event, Integer steamId) {
		List<ServerGame> serverGameList = serverGameEAO.findByAppID(steamId);

		if (!serverGameList.isEmpty()) {
			for (ServerGame serverGame : serverGameList) {
				updateServerGame(event, serverGame);
			}
		} else {
			throw new RuntimeException("There is no game server installed with this SteamID.");
		}
	}
	
	private void updateServerById(MessageReceivedEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);
		
		if (serverGame == null) {
			throw new RuntimeException("There is no game server with this ID.");
		}
		
		updateServerGame(event, serverGame);
	}
	
	private void updateServerGame(MessageReceivedEvent event, ServerGame serverGame) {
		createDiscordUpdaterListener(event, serverGame);
		
		UpdaterServerGameService updaterServerGameService = ServiceProvider.provide(UpdaterServerGameService.class);
		updaterServerGameService.update(serverGame);
	}

	private void createDiscordUpdaterListener(MessageReceivedEvent event, ServerGame serverGame) {
		new UpdateServerListenerAdapter(event, serverGame);
	}
	
	private Long getIdServerGameParameter(String[] commands) {
		Long id = null;
		
		try {
			if (commands.length == 1 && !commands[0].equals("all")) {
				String param = commands[0];
				id = Long.valueOf(param);
			}

		} catch (Exception ex) {
			throw new RuntimeException("Invalid Parameter!");
		}
		
		return id;
	}
	
	private Integer getSteamIdParameter(String[] commands) {
		Integer steamId = null;
		
		try {
			if (commands.length > 1) {
				String param = commands[1];
				steamId = Integer.valueOf(param);
			}

		} catch (Exception ex) {
			throw new RuntimeException("Invalid Parameter!");
		}
		
		return steamId;
	}

	@Override
	public String help() {
		return "'all' - Update all servers.";
	}

}
