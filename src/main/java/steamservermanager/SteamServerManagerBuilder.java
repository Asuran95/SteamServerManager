package steamservermanager;

import java.util.List;

import steamservermanager.events.EventManager;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.ServerRunnerService;
import steamservermanager.updaterservergame.UpdaterServerGameService;
import steamservermanager.utils.LibraryFileHelper;

public class SteamServerManagerBuilder {
	
	private String localLibrary;
	private SteamServerManagerListener listener;
	
	
	public SteamServerManagerBuilder setLocalLibrary(String localLibrary) {
		this.localLibrary = localLibrary;
		
		return this;
	}
	
	public SteamServerManagerBuilder setListener(SteamServerManagerListener listener){
		this.listener = listener;
		 
		return this;
	}
	
	public SteamServerManager build() {
		LibraryFileHelper libraryHelper = new LibraryFileHelper(localLibrary);
		
		List<ServerGame> serverGameLibrary = libraryHelper.loadLibraryFromDisk();
		
		EventManager eventManager = new EventManager(libraryHelper, listener);
		UpdaterServerGameService updaterServerGameService = new UpdaterServerGameService(localLibrary, eventManager);
		ServerRunnerService serverRunnerService = new ServerRunnerService(localLibrary, eventManager);

		return new SteamServerManager(serverGameLibrary, updaterServerGameService, serverRunnerService);
	}
}
