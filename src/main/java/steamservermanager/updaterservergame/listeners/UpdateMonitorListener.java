
package steamservermanager.updaterservergame.listeners;

import steamservermanager.models.ServerGame;

public interface UpdateMonitorListener {
    
	void onNewUpdate(ServerGame serverGame);
    
    void onGetUpdateJob(ServerGame serverGame);
    
    void onCompletedUpdate(ServerGame serverGame);
}
