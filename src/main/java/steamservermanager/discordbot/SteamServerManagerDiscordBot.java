package steamservermanager.discordbot;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import steamservermanager.discordbot.commands.DiscordCommandHandler;
import steamservermanager.discordbot.commands.serverlist.ServerListCommand;
import steamservermanager.discordbot.commands.startserver.StartServerCommand;
import steamservermanager.discordbot.commands.update.UpdateServerCommand;
import steamservermanager.discordbot.listener.DiscordBotListener;

public class SteamServerManagerDiscordBot extends ListenerAdapter {
	
	private Map<String, DiscordCommandHandler> map = new HashMap<>();
	private DiscordBotListener discordBotListener;
	
	public SteamServerManagerDiscordBot(String prefix, DiscordBotListener discordBotListener) {
		this.discordBotListener = discordBotListener;
		
		map.put("update", new UpdateServerCommand());
		map.put("show", new ServerListCommand());
		map.put("server", new StartServerCommand());
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		String commandName = event.getName();
			
		try {
			map.get(commandName).performAction(event);
		} catch (Exception e) {
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
