package steamservermanager.updaterservergame;

import java.util.ArrayList;
import java.util.List;

import steamcmd.SteamCMD;
import steamservermanager.models.ServerGame;
import steamservermanager.models.enums.ServerStatus;
import steamservermanager.updaterservergame.listeners.UpdateMonitorListener;

public class UpdaterServerGame {

    private List<ServerGame> updateList = new ArrayList<>();
    private UpdateMonitorListener listener;
    private SteamCMD steamCmd;
    private UpdateWorker updateThread;

    public UpdaterServerGame(UpdateMonitorListener listener, SteamCMD steamCmd) {
        this.listener = listener;
        this.steamCmd = steamCmd;
    }
    
    public void startUpdater() {
    	updateThread = new UpdateWorker(this, steamCmd);
        updateThread.start();
    }
    
    public synchronized void addUpdate(ServerGame serverGame) {

    	if (!updateList.contains(serverGame)) {
    		serverGame.setStatus(ServerStatus.WAITING);

            updateList.add(serverGame);
            
            listener.onNewUpdate(serverGame);
            
            synchronized (this) {
                notify();
            }
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
        serverGame.setStatus(ServerStatus.UPDATING);    
        listener.onStartUpdate(serverGame);
            
        return serverGame;
    }

    public void completedUpdateJob(ServerGame serverGame) {

        serverGame.setStatus(ServerStatus.STOPPED);

        listener.onCompletedUpdate(serverGame);
            
        updateList.remove(serverGame);
    }
}
