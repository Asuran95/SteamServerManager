package steamservermanager.updaterservergame;

import steamcmd.SteamCMD;
import steamcmd.SteamCMDBuilder;
import steamservermanager.events.EventManager;
import steamservermanager.models.ServerGame;

public class UpdaterServerGameService {

	private UpdaterServerGame updater;
	
	private EventManager eventManager;
	private String localLibrary;
	
	public UpdaterServerGameService(String localLibrary, EventManager eventManager) {
		this.localLibrary = localLibrary;
		this.eventManager = eventManager;
		
		setupUpdater();
	}

	public void update(ServerGame serverGame) {
		
		if (updater == null) {
			setupUpdater();
		}
		
		updater.addUpdate(serverGame);
	}
	
	private void setupUpdater() {
		SteamCMDBuilder builder = 
        		new SteamCMDBuilder().addListener(eventManager.getSteamCMDListener());
		
		SteamCMD steamCmd = builder.build();
		
		updater = new UpdaterServerGame(eventManager.getUpdateMonitorListener(), steamCmd, localLibrary);
		
		updater.startUpdater();
	}
}
