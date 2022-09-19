package steamservermanager.discordbot.commands.serverlist;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.ServerGame;
import steamservermanager.utils.DiscordUtils;
import steamservermanager.utils.ServiceProvider;

public class ServerListCommand extends DiscordCommandHandler {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);

	@Override
	protected void action(MessageReceivedEvent event, String[] commands) {
		replyServerList(event);
	}
	
	private void replyServerList(MessageReceivedEvent event) {
		List<ServerGame> serverList = serverGameEAO.findAll();
		
		MessageChannelUnion channel = event.getChannel();
		
		if (!serverList.isEmpty()) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Server List");
			
			for (ServerGame serverGame : serverList) {
				StringBuilder sb = new StringBuilder();
				sb.append("**ID:** " + serverGame.getIdServerGame() + " ");
				sb.append("**Game:** " + serverGame.getGameName() + " ");
				sb.append("**SteamID:** " + serverGame.getAppID() + " ");
				sb.append("**Status:** " + serverGame.getStatus().name() + " ");
				
				embed.addField(">" +serverGame.getName() + "<", sb.toString(), false);
			}
			
			embed.setFooter(DiscordUtils.getFooterDescription());
			embed.setColor(DiscordUtils.getDefaultEmbedColor());
			
			channel.sendMessageEmbeds(embed.build()).complete();
			
		} else {
			throw new RuntimeException("The server library is empty.");
		}
	}
	
	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
