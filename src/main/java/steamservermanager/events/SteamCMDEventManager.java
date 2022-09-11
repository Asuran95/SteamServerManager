package steamservermanager.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import steamcmd.SteamCMDListener;
import steamservermanager.listeners.SteamServerManagerListener;

public class SteamCMDEventManager implements SteamCMDListener {

	private List<SteamServerManagerListener> steamServerManagerListeners = new ArrayList<>();
	
	public void addListener(SteamServerManagerListener steamServerManagerListener) {
		steamServerManagerListeners.add(steamServerManagerListener);
	}

	 @Override
     public void onStdOut(String out) {
		 
		 for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
			 steamServerManagerListener.onSteamCMDStdOut(out);
		 }
         
         if(out.contains("verifying") 
        		 || out.contains("downloading") 
        		 || out.contains("reconfiguring")
        		 || out.contains("stagging")){
             
             String[] splitOut = out.split(":");
             
             String[] pctStringSplit = splitOut[1].split(" ");
             
             String[] statusStringSplit = splitOut[0].split(" ");
             
             double pct = Double.parseDouble(pctStringSplit[1]);
             
             for (SteamServerManagerListener steamServerManagerListener : steamServerManagerListeners) {
            	 steamServerManagerListener.onStatusSteamCMD(statusStringSplit[4].replace(",", ""), pct);
             }
         }
     }

     @Override
     public String onAuthCode() {
         Scanner sc = new Scanner(System.in);

         String authCode = sc.nextLine();

         sc.close();

         return authCode;
     }

     @Override
     public void onFailedLoginCode() {
         System.err.println("FAILED login with result code Two-factor code mismatch");
     }

     @Override
     public void onInvalidPassword() {
         System.err.println("FAILED login with result code Invalid Password");
     }
}
