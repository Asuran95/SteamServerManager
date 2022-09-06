package steamservermanager.events;

import steamservermanager.eao.SteamServerManagerEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;

public class UpdateMonitorEventManager implements UpdateMonitorListener {

	private SteamServerManagerEAO steamServerManagerEAO;
	private SteamServerManagerListener steamServerManagerListener;
	
	public UpdateMonitorEventManager(SteamServerManagerEAO steamServerManagerEAO, SteamServerManagerListener steamServerManagerListener) {
		this.steamServerManagerEAO = steamServerManagerEAO;
		this.steamServerManagerListener = steamServerManagerListener;
	}

	@Override
    public void onNewUpdate(ServerGame serverGame) {
		steamServerManagerEAO.persistServerGame(serverGame);
		steamServerManagerListener.onServerGameChanged();
    }

    @Override
    public void onGetUpdateJob(ServerGame serverGame) {
    	steamServerManagerEAO.persistServerGame(serverGame);
    	steamServerManagerListener.onServerGameChanged();
    	steamServerManagerListener.onUpdateServer(serverGame);
    }

    @Override
    public void onCompletedUpdate(ServerGame serverGame) {
    	steamServerManagerEAO.persistServerGame(serverGame);
    	steamServerManagerListener.onServerGameChanged();
        steamServerManagerListener.onCompleteUpdateServer();
    }  

}
