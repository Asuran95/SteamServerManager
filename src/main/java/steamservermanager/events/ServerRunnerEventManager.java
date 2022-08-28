package steamservermanager.events;

import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;
import steamservermanager.utils.LibraryFileHelper;

public class ServerRunnerEventManager implements ServerRunnerListener {

	private LibraryFileHelper libraryFileHelper;
	private SteamServerManagerListener steamServerManagerListener;
	
	
	public ServerRunnerEventManager(LibraryFileHelper libraryFileHelper, SteamServerManagerListener steamServerManagerListener) {
		this.libraryFileHelper = libraryFileHelper;
		this.steamServerManagerListener = steamServerManagerListener;
	}

	@Override
    public void onServerStarted(ServerGame serverGame) {
		libraryFileHelper.updateLibraryFile();
		steamServerManagerListener.onUpdateServerStatus();
    }

    @Override
    public void onServerStopped(ServerGame serverGame) {
    	libraryFileHelper.updateLibraryFile();
    	steamServerManagerListener.onUpdateServerStatus();
    }

    @Override
    public void onServerException(ServerGame serverGame) {
    	libraryFileHelper.updateLibraryFile();
    	steamServerManagerListener.onUpdateServerStatus();
    }   

}
