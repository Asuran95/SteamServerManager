package steamservermanager.events;

import java.util.ArrayList;
import java.util.List;

import steamcmd.SteamCMDListener;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;

public class EventManagerService {
	
	private SteamCMDEventManager steamCMDListener;
	private UpdateMonitorEventManager updateMonitorListener;
	private ServerRunnerEventManager serverRunnerListener;
	private List<SteamServerManagerListener> steamServerManagerListeners = new ArrayList<>();
	
	public EventManagerService() {
		this.steamCMDListener = new SteamCMDEventManager();
		this.updateMonitorListener = new UpdateMonitorEventManager();
		this.serverRunnerListener = new ServerRunnerEventManager();
	}
	
	public void addListener(SteamServerManagerListener steamServerManagerListener) {
		steamCMDListener.addListener(steamServerManagerListener);
		updateMonitorListener.addListener(steamServerManagerListener);
		serverRunnerListener.addListener(steamServerManagerListener);
		steamServerManagerListeners.add(steamServerManagerListener);
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

	public List<SteamServerManagerListener> getSteamServerManagerListeners() {
		return steamServerManagerListeners;
	}

	public void setSteamServerManagerListeners(List<SteamServerManagerListener> steamServerManagerListeners) {
		this.steamServerManagerListeners = steamServerManagerListeners;
	}
}
