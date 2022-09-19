package steamservermanager.updaterservergame;

import java.io.File;

import steamcmd.SteamCMD;
import steamservermanager.models.ServerGame;

public class UpdateWorker extends Thread {

    private UpdaterServerGame monitor;
    private SteamCMD steamCmd;

    public UpdateWorker(UpdaterServerGame monitor, SteamCMD steamCmd) {
        this.monitor = monitor;
        this.steamCmd = steamCmd;
    }

    @Override
    public void run() {
        while (true) {
            ServerGame updateJob = waitForUpdate();
            
            steamCmd.start();
            
            String localLibrary = updateJob.getManagerSettings().getLocalLibrary();

            File localDir = new File(localLibrary + File.separator + updateJob.getLocalName());

            if (!localDir.exists()) {
                localDir.mkdir();
            }

            steamCmd.forceInstallDir(localLibrary + File.separator + updateJob.getLocalName());
            steamCmd.loginAnonymous();
            steamCmd.appUpdate(updateJob.getAppID(), "validate");
            steamCmd.logout();
            
            monitor.completedUpdateJob(updateJob);
        }
    }
    
    private  ServerGame waitForUpdate() {
    	ServerGame updateJob;
    	
    	synchronized (monitor) {
    		updateJob = monitor.getUpdateJob();
        }
    	
    	return updateJob;
    }
}
