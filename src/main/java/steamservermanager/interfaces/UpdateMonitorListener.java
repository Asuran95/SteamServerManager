
package steamservermanager.interfaces;

import steamservermanager.models.ServerGame;

public interface UpdateMonitorListener {
    void onNewUpdate(ServerGame server);
    void onGetUpdateJob(ServerGame server);
    void onCompleteJob(ServerGame server);
}
