package steamservermanager.events;

import java.util.ArrayList;
import java.util.List;

import steamservermanager.eao.ServerGameEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;
import steamservermanager.utils.ServiceProvider;

public class UpdateMonitorEventManager implements UpdateMonitorListener {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);
	
	private List<SteamServerManagerListener> steamServerManagerListeners = new ArrayList<>();
	
	public void addListener(SteamServerManagerListener steamServerManagerListener) {
		steamServerManagerListeners.add(steamServerManagerListener);
	}

	@Override
    public void onNewUpdate(ServerGame serverGame) {
		serverGameEAO.merge(serverGame);
		
		for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			steamServerManagerListener.onServerGameChanged();
		}
    }

    @Override
    public void onStartUpdate(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
	    	steamServerManagerListener.onServerGameChanged();
	    	steamServerManagerListener.onStartUpdateServerGame(serverGame);
    	}
    }

    @Override
    public void onCompletedUpdate(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
	    	steamServerManagerListener.onServerGameChanged();
	        steamServerManagerListener.onCompletedUpdateServerGame();
    	}
    }  
}
