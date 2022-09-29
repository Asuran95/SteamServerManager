package steamservermanager.discordbot.commands.serverlist;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.ServerGame;
import steamservermanager.utils.DiscordUtils;
import steamservermanager.utils.ServiceProvider;

public class ServerListCommand extends DiscordCommandHandler {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);

	@Override
	protected void action(SlashCommandInteractionEvent event) {
		String subcommandName = event.getSubcommandName();
		
		if (subcommandName.equals("list")) {

			replyServerList(event);
			
			return;
		} 
	}
	
	private void replyServerList(SlashCommandInteractionEvent event) {
		List<ServerGame> serverList = serverGameEAO.findAll();
		
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
			
			event.replyEmbeds(embed.build()).queue();
			
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
