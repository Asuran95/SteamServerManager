package steamservermanager;

import steamservermanager.models.ServerGame;
import java.io.File;
import java.io.IOException;

import steamcmd.SteamCMD;

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

        try {
            steamCmd.loginAnonymous();
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            ServerGame updateJob = null;

            synchronized (monitor) {
                updateJob = monitor.getUpdateJob();
            }

            System.out.println("Atualizando " + updateJob.getServerName());

            try {

                File localDir = new File(localLibrary + File.separator + updateJob.getServerName());

                if (!localDir.exists()) {
                    localDir.mkdir();
                }

                steamCmd.forceInstallDir(localLibrary + File.separator + updateJob.getServerName());
                steamCmd.appUpdate(updateJob.getGameId(), "validate");

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

            monitor.completedUpdateJob(updateJob);
        }
    }

}
