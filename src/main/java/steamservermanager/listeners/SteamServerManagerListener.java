package steamservermanager.listeners;

import steamservermanager.dtos.ServerGameDTO;

public interface SteamServerManagerListener {
	
    void onSteamCMDStdOut(String out);
    
    void onStatusSteamCMD(String status, double pctUpdate);
    
    void onServerGameChanged(ServerGameDTO serverGameDTO);
    
    void onStartUpdateServerGame(ServerGameDTO serverGameDTO);
    
    void onCompletedUpdateServerGame(ServerGameDTO serverGameDTO);  
    
    void onDiscordBotChangedStatus(String status);
    
    void onDiscordBotStarted();
    
    void onDiscordBotStopped();
}
