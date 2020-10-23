import steamservermanager.ServerGame;
import steamservermanager.SteamServerManager;
import steamservermanager.exceptions.ServerNameException;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub /home/asu/librarytest/
		
		SteamServerManager serverManager = new SteamServerManager("/home/asu/librarytest");
		
		
		ServerGame server1 = new ServerGame(90, "servidor do fulaninhos", "");

		Thread.sleep(2000);
		
		try {
			serverManager.newServerGame(server1);
		} catch (ServerNameException e) {}

		
		ServerGame server2 = new ServerGame(232130, "kf2 server", "");
		
		
		try {
			serverManager.newServerGame(server2);
		} catch (ServerNameException e) {}
		
	}

}
