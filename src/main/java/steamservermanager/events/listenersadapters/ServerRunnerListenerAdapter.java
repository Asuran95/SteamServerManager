package steamservermanager.events.listenersadapters;

import java.util.List;

import steamservermanager.dtos.ServerGameDTO;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;
import steamservermanager.utils.ObjectUtils;
import steamservermanager.utils.ServiceProvider;

public class ServerRunnerListenerAdapter implements ServerRunnerListener {

	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);

	private List<SteamServerManagerListener> steamServerManagerListeners;
	
	public ServerRunnerListenerAdapter(List<SteamServerManagerListener> steamServerManagerListeners) {
		this.steamServerManagerListeners = steamServerManagerListeners;
	}

	@Override
    public void onServerStarted(ServerGame serverGame) {
		serverGameEAO.merge(serverGame);
		
		ServerGameDTO serverGameDTO = ObjectUtils.copyObject(serverGame, ServerGameDTO.class);
		
		for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			steamServerManagerListener.onServerGameChanged(serverGameDTO);
		}
    }

    @Override
    public void onServerStopped(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	ServerGameDTO serverGameDTO = ObjectUtils.copyObject(serverGame, ServerGameDTO.class);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
    		steamServerManagerListener.onServerGameChanged(serverGameDTO);
    	}
    }

    @Override
    public void onServerException(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	ServerGameDTO serverGameDTO = ObjectUtils.copyObject(serverGame, ServerGameDTO.class);
    	
    	for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
    		steamServerManagerListener.onServerGameChanged(serverGameDTO);
    	}
    }   

}
