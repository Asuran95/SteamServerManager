/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamservermanager.interfaces;

import steamservermanager.models.ServerGame;

/**
 *
 * @author asu
 */
public interface ServerRunnerListener {
    void onServerStart(ServerGame serverGame);
    void onServerStopped(ServerGame serverGame);
    void onServerException(ServerGame serverGame);
}
