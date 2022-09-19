package steamservermanager.events.listenersadapters;

import java.util.List;

import steamservermanager.discordbot.listener.DiscordBotListener;
import steamservermanager.listeners.SteamServerManagerListener;

public class DiscordBotListenerAdapter implements DiscordBotListener {

	private List<SteamServerManagerListener> steamServerManagerListeners;
	
	public DiscordBotListenerAdapter(List<SteamServerManagerListener> steamServerManagerListeners) {
		this.steamServerManagerListeners = steamServerManagerListeners;
	}
	
	@Override
	public void onDiscordBotChangedStatus(String status) {
		for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			steamServerManagerListener.onDiscordBotChangedStatus(status);
		}
	}

	@Override
	public void onDiscordBotStarted() {
		for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			steamServerManagerListener.onDiscordBotStarted();
		}
	}

	@Override
	public void onDiscordBotStopped() {
		for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			steamServerManagerListener.onDiscordBotStopped();
		}
	}
}
