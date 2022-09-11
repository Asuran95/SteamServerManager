package steamservermanager.listeners;

import steamservermanager.models.ServerGame;

public interface SteamServerManagerListener {
   
	void onServerGameChanged();
    
    void onSteamCMDStdOut(String out);
    
    void onStatusSteamCMD(String status, double pctUpdate);
    
    void onStartUpdateServerGame(ServerGame serverGame);
    
    void onCompletedUpdateServerGame();  
}
