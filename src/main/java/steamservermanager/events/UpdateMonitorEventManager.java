package steamservermanager.events;

import java.util.ArrayList;
import java.util.List;

import steamservermanager.eao.ServerGameEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;
import steamservermanager.utils.ObjectUtils;
import steamservermanager.utils.ServiceProvider;
import steamservermanager.vos.ServerGameVO;

public class UpdateMonitorEventManager implements UpdateMonitorListener {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);
	
	private List<SteamServerManagerListener> steamServerManagerListeners = new ArrayList<>();
	
	public void addListener(SteamServerManagerListener steamServerManagerListener) {
		steamServerManagerListeners.add(steamServerManagerListener);
	}

	@Override
    public void onNewUpdate(ServerGame serverGame) {
		serverGameEAO.merge(serverGame);
		
		ServerGameVO serverGameVO = ObjectUtils.copyObject(serverGame, ServerGameVO.class);
		
		for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			steamServerManagerListener.onServerGameChanged(serverGameVO);
		}
    }

    @Override
    public void onStartUpdate(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	ServerGameVO serverGameVO = ObjectUtils.copyObject(serverGame, ServerGameVO.class);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
	    	steamServerManagerListener.onServerGameChanged(serverGameVO);
	    	steamServerManagerListener.onStartUpdateServerGame(serverGameVO);
    	}
    }

    @Override
    public void onCompletedUpdate(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	ServerGameVO serverGameVO = ObjectUtils.copyObject(serverGame, ServerGameVO.class);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
	    	steamServerManagerListener.onServerGameChanged(serverGameVO);
	        steamServerManagerListener.onCompletedUpdateServerGame(serverGameVO);
    	}
    }  
}
