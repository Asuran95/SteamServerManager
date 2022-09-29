package steamservermanager.discordbot.commands.update;

import java.util.List;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
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
	protected void action(SlashCommandInteractionEvent event) {

		String subcommandName = event.getSubcommandName();
		
		if (subcommandName.equals("all")) {
			
			updateAllServers(event);
			
			return;
			
		} else if (subcommandName.equals("game")) {
			
			int steamId = event.getOption("name").getAsInt();
			updateServersbySteamID(event, steamId);
			
			return;
			
		} else if (subcommandName.equals("server")) {
			
			long id = event.getOption("name").getAsLong();
			updateServerById(event, id);
			
			return;
			
		}
		
	}

	private void updateAllServers(SlashCommandInteractionEvent event) {
		List<ServerGame> serverGameList = serverGameEAO.findAll();

		if (!serverGameList.isEmpty()) {
			for (ServerGame serverGame : serverGameList) {
				updateServerGame(event, serverGame);
			}
		} else {
			throw new RuntimeException("There is no game server installed.");
		}
	}

	private void updateServersbySteamID(SlashCommandInteractionEvent event, Integer steamId) {
		List<ServerGame> serverGameList = serverGameEAO.findByAppID(steamId);

		if (!serverGameList.isEmpty()) {
			for (ServerGame serverGame : serverGameList) {
				updateServerGame(event, serverGame);
			}
		} else {
			throw new RuntimeException("There is no game server installed with this SteamID.");
		}
	}

	private void updateServerById(SlashCommandInteractionEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);

		if (serverGame == null) {
			throw new RuntimeException("There is no game server with this ID.");
		}

		updateServerGame(event, serverGame);
	}

	private void updateServerGame(SlashCommandInteractionEvent event, ServerGame serverGame) {
		createDiscordUpdaterListener(event, serverGame);

		UpdaterServerGameService updaterServerGameService = ServiceProvider.provide(UpdaterServerGameService.class);
		updaterServerGameService.update(serverGame);
	}

	private void createDiscordUpdaterListener(SlashCommandInteractionEvent event, ServerGame serverGame) {
		new UpdateServerListenerAdapter(event, serverGame);
	}

	@Override
	public String help() {
		return "'all' - Update all servers.";
	}

}