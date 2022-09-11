package steamservermanager.events;

import java.util.ArrayList;
import java.util.List;

import steamservermanager.eao.ServerGameEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;
import steamservermanager.utils.ServiceProvider;

public class ServerRunnerEventManager implements ServerRunnerListener {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);

	private List<SteamServerManagerListener> steamServerManagerListeners = new ArrayList<>();
	
	public void addListener(SteamServerManagerListener steamServerManagerListener) {
		steamServerManagerListeners.add(steamServerManagerListener);
	}

	@Override
    public void onServerStarted(ServerGame serverGame) {
		serverGameEAO.merge(serverGame);
		
		for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			steamServerManagerListener.onServerGameChanged();
		}
    }

    @Override
    public void onServerStopped(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
    		steamServerManagerListener.onServerGameChanged();
    	}
    }

    @Override
    public void onServerException(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
    		steamServerManagerListener.onServerGameChanged();
    	}
    }   

}
