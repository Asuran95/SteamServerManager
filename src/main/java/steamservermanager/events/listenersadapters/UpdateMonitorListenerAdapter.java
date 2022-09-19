package steamservermanager.events.listenersadapters;

import java.util.List;

import steamservermanager.dtos.ServerGameDTO;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;
import steamservermanager.utils.ObjectUtils;
import steamservermanager.utils.ServiceProvider;

public class UpdateMonitorListenerAdapter implements UpdateMonitorListener {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);
	
	private List<SteamServerManagerListener> steamServerManagerListeners;

	public UpdateMonitorListenerAdapter(List<SteamServerManagerListener> steamServerManagerListeners) {
		this.steamServerManagerListeners = steamServerManagerListeners;
	}

	@Override
    public void onNewUpdate(ServerGame serverGame) {
		serverGameEAO.merge(serverGame);
		
		ServerGameDTO serverGameVO = ObjectUtils.copyObject(serverGame, ServerGameDTO.class);
		
		for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			steamServerManagerListener.onServerGameChanged(serverGameVO);
		}
    }

    @Override
    public void onStartUpdate(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	ServerGameDTO serverGameVO = ObjectUtils.copyObject(serverGame, ServerGameDTO.class);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
	    	steamServerManagerListener.onServerGameChanged(serverGameVO);
	    	steamServerManagerListener.onStartUpdateServerGame(serverGameVO);
    	}
    }

    @Override
    public void onCompletedUpdate(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	ServerGameDTO serverGameVO = ObjectUtils.copyObject(serverGame, ServerGameDTO.class);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
	    	steamServerManagerListener.onServerGameChanged(serverGameVO);
	        steamServerManagerListener.onCompletedUpdateServerGame(serverGameVO);
    	}
    }  
}
