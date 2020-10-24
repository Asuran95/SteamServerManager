import steamservermanager.ServerRunner;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		
		ServerRunner run = new ServerRunner(null);
		
		
		
		run.start();
		
		
		run.join();
		
		/*
		SteamServerManager serverManager = new SteamServerManager("/mnt/steamcompat/librarytest");
		
		
		ServerGame server1 = new ServerGame(90, "servidor do fulaninhos", "");

		Thread.sleep(2000);
		
		try {
			serverManager.newServerGame(server1);
		} catch (ServerNameException e) {}

		
		ServerGame server2 = new ServerGame(232130, "kf2 server", "");
		
		
		try {
			serverManager.newServerGame(server2);
		} catch (ServerNameException e) {}*/
		
	}

}
