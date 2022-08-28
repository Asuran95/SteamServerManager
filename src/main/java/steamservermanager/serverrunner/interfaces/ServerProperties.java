
package steamservermanager.serverrunner.interfaces;

import steamservermanager.listeners.ServerGameConsoleListener;
import steamservermanager.models.ServerGame;

public interface ServerProperties {
    ServerMessageDispatcher setListener(ServerGameConsoleListener listener);
    
    ServerGame getServerGame();
    
    boolean isRunning();
}
