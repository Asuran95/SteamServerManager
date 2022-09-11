package steamservermanager.listeners;

import steamservermanager.vos.ServerGameVO;

public interface SteamServerManagerListener {
	
    void onSteamCMDStdOut(String out);
    
    void onStatusSteamCMD(String status, double pctUpdate);
    
    void onServerGameChanged(ServerGameVO serverGame);
    
    void onStartUpdateServerGame(ServerGameVO serverGame);
    
    void onCompletedUpdateServerGame(ServerGameVO serverGame);  
}
