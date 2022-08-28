package steamservermanager.events;

import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;
import steamservermanager.utils.LibraryFileHelper;

public class UpdateMonitorEventManager implements UpdateMonitorListener {

	private LibraryFileHelper libraryFileHelper;
	private SteamServerManagerListener steamServerManagerListener;
	
	public UpdateMonitorEventManager(LibraryFileHelper libraryFileHelper, SteamServerManagerListener steamServerManagerListener) {
		this.libraryFileHelper = libraryFileHelper;
		this.steamServerManagerListener = steamServerManagerListener;
	}

	@Override
    public void onNewUpdate(ServerGame server) {
		libraryFileHelper.updateLibraryFile();
		steamServerManagerListener.onUpdateServerStatus();
    }

    @Override
    public void onGetUpdateJob(ServerGame server) {
    	libraryFileHelper.updateLibraryFile();
    	steamServerManagerListener.onUpdateServerStatus();
    	steamServerManagerListener.onUpdateServer(server);
    }

    @Override
    public void onCompletedUpdate(ServerGame server) {
    	libraryFileHelper.updateLibraryFile();
    	steamServerManagerListener.onUpdateServerStatus();
        steamServerManagerListener.onCompleteUpdateServer();
    }  

}
