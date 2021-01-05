package steamservermanager.interfaces;

public interface SteamServerManagerListener {
    
    void onUpdateServerStatus();
    void onSteamCMDStdOut(String out);
    void onReady();
    
}
