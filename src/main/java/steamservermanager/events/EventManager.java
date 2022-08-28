package steamservermanager.events;

import steamcmd.SteamCMDListener;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;
import steamservermanager.utils.LibraryFileHelper;

public class EventManager {
	
	private ServerRunnerListener serverRunnerListener;
	private SteamCMDListener steamCMDListener;
	private UpdateMonitorListener updateMonitorListener;
	
	public EventManager(LibraryFileHelper libraryFileHelper, SteamServerManagerListener steamServerManagerListener) {
		this.serverRunnerListener = new ServerRunnerEventManager(libraryFileHelper, steamServerManagerListener);
		this.steamCMDListener = new SteamCMDEventManager(steamServerManagerListener);
		this.updateMonitorListener = new UpdateMonitorEventManager(libraryFileHelper, steamServerManagerListener);
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
