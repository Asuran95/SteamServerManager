package steamservermanager.updaterservergame;

import java.util.ArrayList;
import java.util.List;

import steamcmd.SteamCMD;
import steamservermanager.models.ServerGame;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;
import steamservermanager.utils.Status;

public class UpdaterServerGame {

    private List<ServerGame> updateList = new ArrayList<>();
    private UpdateMonitorListener listener;
    private SteamCMD steamCmd;
    private String localLibrary;
    private UpdateWorker updateThread;

    public UpdaterServerGame(UpdateMonitorListener listener, SteamCMD steamCmd, String localLibrary) {
        this.listener = listener;
        this.steamCmd = steamCmd;
        this.localLibrary = localLibrary;
    }
    
    public void startUpdater() {
    	updateThread = new UpdateWorker(this, localLibrary, steamCmd);
        updateThread.start();
    }
    
    public void addUpdate(ServerGame serverGame) {

        serverGame.setStatus(Status.WAITING);

        updateList.add(serverGame);
        
        listener.onNewUpdate(serverGame);
        
        synchronized (this) {
            notify();
        }
    }

    public ServerGame getUpdateJob() {

        if (updateList.size() == 0) {
        	
        	steamCmd.stop();
        	
            try {
                wait();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        ServerGame serverGame = updateList.get(0); 
        serverGame.setStatus(Status.UPDATING);    
        listener.onGetUpdateJob(serverGame);
            
        return serverGame;
    }

    public void completedUpdateJob(ServerGame serverGame) {

        serverGame.setStatus(Status.STOPPED);

        listener.onCompletedUpdate(serverGame);
            
        updateList.remove(serverGame);
    }
}
