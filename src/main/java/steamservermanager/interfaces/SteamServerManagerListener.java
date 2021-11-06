package steamservermanager.interfaces;

import steamservermanager.models.ServerGame;



public interface SteamServerManagerListener {
    
    void onUpdateServerStatus();
    void onSteamCMDStdOut(String out);
    void onReady();
    void onStatusSteamCMD(String status, double pctUpdate);
    void onUpdateServer(ServerGame serverGame);
    void onCompleteUpdateServer();
    
}
