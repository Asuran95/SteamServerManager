package steamservermanager.services;

import steamcmd.SteamCMD;
import steamcmd.SteamCMDBuilder;
import steamservermanager.events.EventManagerService;
import steamservermanager.models.ServerGame;
import steamservermanager.updaterservergame.UpdaterServerGame;
import steamservermanager.utils.ServiceProvider;

public class UpdaterServerGameService {

	private UpdaterServerGame updater;
	private EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);
	private ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);
	
	public UpdaterServerGameService() {
		setupUpdater();
	}

	public void update(ServerGame serverGame) {
		
		if (updater == null) {
			setupUpdater();
		}
		serverRunnerService.stopServer(serverGame);
		updater.addUpdate(serverGame);
	}
	
	private void setupUpdater() {
		SteamCMDBuilder builder = 
        		new SteamCMDBuilder().addListener(eventManager.getSteamCMDListener());
		
		SteamCMD steamCmd = builder.build();
		
		updater = new UpdaterServerGame(eventManager.getUpdateMonitorListener(), steamCmd);
		
		updater.startUpdater();
	}
}
