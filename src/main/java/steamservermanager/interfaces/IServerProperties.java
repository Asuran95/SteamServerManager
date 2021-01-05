/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamservermanager.interfaces;

import steamservermanager.models.ServerGameViewer;

/**
 *
 * @author asu
 */
public interface IServerProperties {
    
    StandardInputInterface setListener(ServerRunnerListener listener);
    ServerGameViewer getServerGame();
    boolean isRunning();
    
}
