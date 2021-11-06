
package steamservermanager.interfaces;

import steamservermanager.models.ServerGame;

public interface ServerProperties {
    
    StandardInputInterface setListener(StandardOutputInterface listener);
    ServerGame getServerGame();
    boolean isRunning();

}
