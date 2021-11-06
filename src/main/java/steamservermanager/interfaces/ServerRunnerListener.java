
package steamservermanager.interfaces;

import steamservermanager.models.ServerGame;

public interface ServerRunnerListener {
    void onServerStart(ServerGame serverGame);
    void onServerStopped(ServerGame serverGame);
    void onServerException(ServerGame serverGame);
}
