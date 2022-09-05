package steamservermanager.events;

import steamservermanager.eao.SteamServerManagerEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.listeners.ServerRunnerListener;

public class ServerRunnerEventManager implements ServerRunnerListener {

	private SteamServerManagerEAO steamServerManagerEAO;
	private SteamServerManagerListener steamServerManagerListener;
	
	
	public ServerRunnerEventManager(SteamServerManagerEAO steamServerManagerEAO, SteamServerManagerListener steamServerManagerListener) {
		this.steamServerManagerEAO = steamServerManagerEAO;
		this.steamServerManagerListener = steamServerManagerListener;
	}

	@Override
    public void onServerStarted(ServerGame serverGame) {
		steamServerManagerEAO.persistServerGame(serverGame);
		steamServerManagerListener.onUpdateServerStatus();
    }

    @Override
    public void onServerStopped(ServerGame serverGame) {
    	steamServerManagerEAO.persistServerGame(serverGame);
    	steamServerManagerListener.onUpdateServerStatus();
    }

    @Override
    public void onServerException(ServerGame serverGame) {
    	steamServerManagerEAO.persistServerGame(serverGame);
    	steamServerManagerListener.onUpdateServerStatus();
    }   

}
