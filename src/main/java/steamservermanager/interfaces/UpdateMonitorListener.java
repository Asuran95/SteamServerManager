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
public interface UpdateMonitorListener {
    void onNewUpdate(ServerGame server);
    void onGetUpdateJob(ServerGame server);
    void onCompleteJob(ServerGame server);
}
