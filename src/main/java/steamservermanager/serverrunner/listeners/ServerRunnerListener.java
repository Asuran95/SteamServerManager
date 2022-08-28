
package steamservermanager.serverrunner.listeners;

import steamservermanager.models.ServerGame;

public interface ServerRunnerListener {
    void onServerStarted(ServerGame serverGame);
    
    void onServerStopped(ServerGame serverGame);
    
    void onServerException(ServerGame serverGame);
}
