package steamservermanager.events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import steamcmd.SteamCMDListener;
import steamservermanager.discordbot.listener.DiscordBotListener;
import steamservermanager.events.listenersadapters.DiscordBotListenerAdapter;
import steamservermanager.events.listenersadapters.ServerRunnerListenerAdapter;
import steamservermanager.events.listenersadapters.SteamCMDListenerAdapter;
import steamservermanager.events.listenersadapters.UpdateMonitorListenerAdapter;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;

public class EventManagerService {
	
	private SteamCMDListener steamCMDListener;
	private UpdateMonitorListener updateMonitorListener;
	private ServerRunnerListener serverRunnerListener;
	private DiscordBotListener discordBotListener;

	private List<SteamServerManagerListener> steamServerManagerListeners = new CopyOnWriteArrayList<>();
	
	public EventManagerService() {
		this.steamCMDListener = new SteamCMDListenerAdapter(steamServerManagerListeners);
		this.updateMonitorListener = new UpdateMonitorListenerAdapter(steamServerManagerListeners);
		this.serverRunnerListener = new ServerRunnerListenerAdapter(steamServerManagerListeners);
		this.discordBotListener = new DiscordBotListenerAdapter(steamServerManagerListeners);
	}
	
	public void addListener(SteamServerManagerListener steamServerManagerListener) {
		steamServerManagerListeners.add(steamServerManagerListener);
		System.out.println(steamServerManagerListeners.size());
	}
	
	public void removeListener(SteamServerManagerListener steamServerManagerListener) {
		steamServerManagerListeners.remove(steamServerManagerListener);
		System.out.println(steamServerManagerListeners.size());
	}

	public ServerRunnerListener getServerRunnerListener() {
		return serverRunnerListener;
	}
	
	public SteamCMDListener getSteamCMDListener() {
		return steamCMDListener;
	}

	public UpdateMonitorListener getUpdateMonitorListener() {
		return updateMonitorListener;
	}

	public DiscordBotListener getDiscordBotListener() {
		return discordBotListener;
	}

	public List<SteamServerManagerListener> getSteamServerManagerListeners() {
		return steamServerManagerListeners;
	}
}
