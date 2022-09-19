package steamservermanager.discordbot;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.discordbot.commands.serverlist.ServerListCommand;
import steamservermanager.discordbot.commands.startserver.StartServerCommand;
import steamservermanager.discordbot.commands.update.UpdateServerCommand;
import steamservermanager.discordbot.listener.DiscordBotListener;

public class SteamServerManagerDiscordBot extends ListenerAdapter {
	
	private Map<String, DiscordCommandHandler> map = new HashMap<>();
	private String prefix;
	private DiscordBotListener discordBotListener;
	
	public SteamServerManagerDiscordBot(String prefix, DiscordBotListener discordBotListener) {
		this.prefix = prefix;
		this.discordBotListener = discordBotListener;
		
		map.put("update", new UpdateServerCommand());
		map.put("serverlist", new ServerListCommand());
		map.put("startserver", new StartServerCommand());
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentDisplay();
		
		if (message.contains(prefix) && !event.getAuthor().isBot()) {
			String[] splitMessage = message.split(" ");
			
			if (splitMessage[0].equals(prefix) && splitMessage.length > 1) {
				if (map.containsKey(splitMessage[1])) {
					
					if (splitMessage.length > 2 && splitMessage[2].equals("help")) {
						
						String help = map.get(splitMessage[1]).help();
						
						MessageChannel channel = event.getChannel();
						
						if (help != null) {
							channel.sendMessage(help).mentionRepliedUser(true).complete();
						}
						
					}  else {
						message = message.replace(prefix, "");
						message = message.replace(splitMessage[1], "");
						
						String[] commands = message.trim().split(" ");
						
						map.get(splitMessage[1]).performAction(event, commands);
					}
					
				} else if (splitMessage[1].equals("help")) {
					
				
				} else {
					MessageChannel channel = event.getChannel();
					
					channel.sendMessage("Não tem esse comando, burro.").mentionRepliedUser(true).complete();
				}
			}
		}
	}
	
	@Override
	public void onStatusChange(StatusChangeEvent event) {
		Status status = event.getNewStatus();
		
		discordBotListener.onDiscordBotChangedStatus(status.name());
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		discordBotListener.onDiscordBotStarted();
	}
	
	@Override
	public void onShutdown(ShutdownEvent event) {
		discordBotListener.onDiscordBotStopped();
	}
	
	@Override
	public void onException(ExceptionEvent event) {
		String message = event.getCause().getMessage();
		
		//discordBotListener.onDiscordBotChangedStatus(message);
		
	}
}
