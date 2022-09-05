
package steamservermanager.serverrunner.interfaces;

import steamservermanager.listeners.ServerGameConsoleListener;
import steamservermanager.vos.ServerGameVO;

public interface ServerProperties {
    ServerMessageDispatcher setListener(ServerGameConsoleListener listener);
    
    ServerGameVO getServerGame();
    
    boolean isRunning();
}
