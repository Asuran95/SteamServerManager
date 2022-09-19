
package steamservermanager.serverrunner.interfaces;

import steamservermanager.dtos.ServerGameDTO;
import steamservermanager.listeners.ServerGameConsoleListener;

public interface ServerProperties {
    ServerMessageDispatcher setListener(ServerGameConsoleListener listener);
    
    ServerGameDTO getServerGame();
    
    boolean isRunning();
}
