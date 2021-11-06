
package steamservermanager.interfaces;

import steamservermanager.models.ServerGameViewer;

public interface ServerProperties {
    
    StandardInputInterface setListener(StandardOutputInterface listener);
    ServerGameViewer getServerGame();
    boolean isRunning();

}
