package steamservermanager.discordbot.commands.update;

import java.util.List;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.ServerGame;
import steamservermanager.services.UpdaterServerGameService;
import steamservermanager.utils.ServiceProvider;

public class UpdateAllServersCommand extends DiscordCommandHandler {
	
	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void action(SlashCommandInteractionEvent event) {
		updateAllServers(event);
	}
	
	private void updateAllServers(SlashCommandInteractionEvent event) {
		List<ServerGame> serverGameList = serverGameEAO.findAll();

		if (!serverGameList.isEmpty()) {
			for (ServerGame serverGame : serverGameList) {
				updateServerGame(event, serverGame);
			}
		} else {
			//throw new RuntimeException("There is no game server installed.");
			event.reply("There is no game server installed.").setEphemeral(true).queue();
		}
	}
	
	private void updateServerGame(SlashCommandInteractionEvent event, ServerGame serverGame) {
		createDiscordUpdaterListener(event, serverGame);
		
		UpdaterServerGameService updaterServerGameService = ServiceProvider.provide(UpdaterServerGameService.class);
		updaterServerGameService.update(serverGame);
	}

	private void createDiscordUpdaterListener(SlashCommandInteractionEvent event, ServerGame serverGame) {
		new UpdateServerListenerAdapter(event, serverGame);
	}
}
