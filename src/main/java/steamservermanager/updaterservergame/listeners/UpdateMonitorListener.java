
package steamservermanager.updaterservergame.listeners;

import steamservermanager.models.ServerGame;

public interface UpdateMonitorListener {
    
	void onNewUpdate(ServerGame server);
    
    void onGetUpdateJob(ServerGame server);
    
    void onCompletedUpdate(ServerGame server);
}
