package steamservermanager;

import java.io.File;

import steamcmd.SteamCMD;
import steamservermanager.models.ServerGame;

public class UpdateThread extends Thread {

    private UpdateMonitor monitor;
    private String localLibrary;
    private SteamCMD steamCmd;

    public UpdateThread(UpdateMonitor monitor, String localLibrary, SteamCMD steamCmd) {
        this.monitor = monitor;
        this.localLibrary = localLibrary;
        this.steamCmd = steamCmd;
    }

    @Override
    public void run() {
        while (true) {
            ServerGame updateJob = null;

            synchronized (monitor) {
                updateJob = monitor.getUpdateJob();
            }

            System.out.println("Atualizando " + updateJob.getServerName());

            File localDir = new File(localLibrary + File.separator + updateJob.getServerName());

            if (!localDir.exists()) {
                localDir.mkdir();
            }

            steamCmd.forceInstallDir(localLibrary + File.separator + updateJob.getServerName());
            
            steamCmd.loginAnonymous();
            
            steamCmd.appUpdate(updateJob.getAppID(), "validate");

            monitor.completedUpdateJob(updateJob);
        }
    }

}
