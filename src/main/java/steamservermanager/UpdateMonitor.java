package steamservermanager;

import steamservermanager.models.ServerGame;
import java.util.ArrayList;
import java.util.List;
import steamservermanager.interfaces.UpdateMonitorListener;

public class UpdateMonitor {

    private List<ServerGame> updateList = new ArrayList<>();
    private UpdateMonitorListener listener;

    public UpdateMonitor(UpdateMonitorListener listener) {
        this.listener = listener;
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
            try {
                System.out.println("Aguardando");
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

        listener.onCompleteJob(serverGame);
            
        updateList.remove(serverGame);
    }
}
