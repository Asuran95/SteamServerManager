package steamservermanager.discordbot.commands.update;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.MessageEditAction;
import steamservermanager.dtos.ServerGameDTO;
import steamservermanager.events.EventManagerService;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.models.enums.ServerStatus;
import steamservermanager.utils.DiscordUtils;
import steamservermanager.utils.ServiceProvider;

public class UpdateServerListenerAdapter implements SteamServerManagerListener{

	private SlashCommandInteractionEvent event;
	private ServerGame serverGame;
	private ServerGameDTO serverGameStartedUpdate;
	private Message message;
	
	private EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);
	
	public UpdateServerListenerAdapter(SlashCommandInteractionEvent event, ServerGame serverGame) {
		this.event = event;
		this.serverGame = serverGame;
		
		if (serverGame.getStatus().equals(ServerStatus.UPDATING)) {
			serverGameStartedUpdate = new ServerGameDTO();
			serverGameStartedUpdate.setIdServerGame(serverGame.getIdServerGame());
		}
		
		message = createNewMessage(serverGame);
		eventManager.addListener(this);
	}

	@Override
	public void onSteamCMDStdOut(String out) {}

	@Override
	public void onStatusSteamCMD(String status, double pctUpdate) {
		if (serverGameStartedUpdate != null && serverGameStartedUpdate.getIdServerGame().equals(serverGame.getIdServerGame())) {
			if (message != null) {
				MessageEmbed messageEmbed = createMenssageEmbed(serverGame,  status + ": " + pctUpdate + "%");
				
				MessageEditAction messageEditAction = message.editMessageEmbeds(messageEmbed);
				
				message = editMessage(messageEditAction);
			}
		}
	}

	@Override
	public void onServerGameChanged(ServerGameDTO serverGame) {}

	@Override
	public void onStartUpdateServerGame(ServerGameDTO serverGameVo) {
		if (serverGameVo.getIdServerGame().equals(serverGame.getIdServerGame())) {
			serverGameStartedUpdate = serverGameVo;
		}
	}

	@Override
	public void onCompletedUpdateServerGame(ServerGameDTO serverGameVo) {
		if (serverGameStartedUpdate != null && serverGameStartedUpdate.getIdServerGame().equals(serverGame.getIdServerGame())) {
			MessageEmbed messageEmbed = createMenssageEmbed(serverGame, "Completed! ");
			
			MessageEditAction messageEditAction = message.editMessageEmbeds(messageEmbed);
			
			message = editMessage(messageEditAction);
			
			eventManager.removeListener(this);
		}
	}
	
	private Message createNewMessage(ServerGame serverGame) {
		MessageChannel channel = event.getChannel();

		MessageEmbed messageEmbed = createMenssageEmbed(serverGame, "Waiting for SteamCMD.");
		
		MessageCreateAction messageCreateAction = channel.sendMessageEmbeds(messageEmbed);
		
		return sendNewMessage(messageCreateAction);
	}
	
	private MessageEmbed createMenssageEmbed(ServerGame serverGame, String message) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Update Service");
		embed.setDescription("**Game:** " + serverGame.getGameName());
		embed.addField(serverGame.getName(), message, false);
		embed.setFooter(DiscordUtils.getFooterDescription());
		embed.setColor(DiscordUtils.getDefaultEmbedColor());

		return embed.build();
	}
	
	private Message sendNewMessage(MessageCreateAction messageCreateAction) {
		Message message = null;
		
		try {
			message = messageCreateAction.complete();
			
		} catch (Exception ex) {
			eventManager.removeListener(this);
		}
		
		return message;
	}
	
	private Message editMessage(MessageEditAction messageEditAction) {
		Message message = null;
		
		try {
			message = messageEditAction.complete();
			
		} catch (Exception ex) {
			eventManager.removeListener(this);
		}
		
		return message;
	}

	@Override
	public void onDiscordBotChangedStatus(String status) {}

	@Override
	public void onDiscordBotStarted() {}

	@Override
	public void onDiscordBotStopped() {}

}
