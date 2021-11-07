
package steamservermanager.interfaces.serverrunner;

import steamservermanager.models.ServerGame;

public interface ServerProperties {
    
    ServerMessageDispatcher setListener(ServerGameMessageReceiver listener);
    ServerGame getServerGame();
    boolean isRunning();

}
