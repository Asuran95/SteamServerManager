
import java.io.IOException;
import java.util.Scanner;
import steamservermanager.SteamServerManager;
import steamservermanager.exceptions.ServerNameException;
import steamservermanager.interfaces.StandardInputInterface;
import steamservermanager.models.ServerGame;
import steamservermanager.models.ServerGameViewer;

public class Main {

    public static void main(String[] args) throws InterruptedException, ServerNameException, IOException {
        
        //Scanner sc = new Scanner(System.in);
        
        //String localDir = "/mnt/steamcompat/librarytest";
        
        //SteamServerManager serverManager = new SteamServerManager(localDir);
        
        //ServerGame server = new ServerGame(232130, "kf2 server", "Binaries/Win64/KFGameSteamServer.bin.x86_64 KF-BurningParis");
        
        //ServerGame server = new ServerGame(232250, "tf2 server", "srcds_run -console -game tf +sv_pure 0 +map ctf_turbine +port 27050 +maxplayers 32");
        
        //serverManager.newServerGame(server);

        //ServerRunner run = new ServerRunner(server, localDir);
        

        String teste = "strawberries";
        
       
        
        System.out.println( teste.compareTo("asdasdsd"));
        //System.out.println(teste.substring(5, 2));
        


        //run.start();



        //run.join();

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
