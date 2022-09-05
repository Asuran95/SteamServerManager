package steamservermanager.events;

import steamcmd.SteamCMDListener;
import steamservermanager.eao.SteamServerManagerEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;

public class EventManager {
	
	private ServerRunnerListener serverRunnerListener;
	private SteamCMDListener steamCMDListener;
	private UpdateMonitorListener updateMonitorListener;
	
	public EventManager(SteamServerManagerEAO steamServerManagerEAO, SteamServerManagerListener steamServerManagerListener) {
		this.serverRunnerListener = new ServerRunnerEventManager(steamServerManagerEAO, steamServerManagerListener);
		this.steamCMDListener = new SteamCMDEventManager(steamServerManagerListener);
		this.updateMonitorListener = new UpdateMonitorEventManager(steamServerManagerEAO, steamServerManagerListener);
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
}
