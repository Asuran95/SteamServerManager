package steamservermanager.services;

import steamcmd.SteamCMD;
import steamcmd.SteamCMDBuilder;
import steamservermanager.events.EventManagerService;
import steamservermanager.models.ManagerSettings;
import steamservermanager.models.ServerGame;
import steamservermanager.updaterservergame.UpdaterServerGame;
import steamservermanager.utils.ServiceProvider;

public class UpdaterServerGameService {
	private UpdaterServerGame updater;
	private EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);
	private ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);

	public void update(ServerGame serverGame) {
		if (updater == null) {
			ManagerSettings managerSettings = serverGame.getManagerSettings();

			setupUpdater(managerSettings.getLocalLibrary());
		}
		serverRunnerService.stopServer(serverGame);
		updater.addUpdate(serverGame);
	}

	private void setupUpdater(String localLibrary) {
		SteamCMD steamCmd = new SteamCMDBuilder()
				.setLocalDir(localLibrary)
				.addListener(eventManager.getSteamCMDListener())
				.build();

		updater = new UpdaterServerGame(eventManager.getUpdateMonitorListener(), steamCmd);

		updater.startUpdater();
	}
}
