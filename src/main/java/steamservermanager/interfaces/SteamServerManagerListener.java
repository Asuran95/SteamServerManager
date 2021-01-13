package steamservermanager.interfaces;

import steamservermanager.models.ServerGameViewer;

public interface SteamServerManagerListener {
    
    void onUpdateServerStatus();
    void onSteamCMDStdOut(String out);
    void onReady();
    void onStatusSteamCMD(String status, double pctUpdate);
    void onUpdateServer(ServerGameViewer serverGame);
    void onCompleteUpdateServer();
    
}
