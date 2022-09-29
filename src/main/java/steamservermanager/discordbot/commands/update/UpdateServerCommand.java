package steamservermanager.discordbot.commands.update;

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
		long id = event.getOption("serverid").getAsLong();
		
		updateServerById(event, id);
	}
	
	private void updateServerById(SlashCommandInteractionEvent event, Long id) {
		ServerGame serverGame = serverGameEAO.find(id);
		
		if (serverGame == null) {
			//throw new RuntimeException("There is no game server with this ID.");
			event.reply("There is no game server with this ID.").setEphemeral(true).queue();
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