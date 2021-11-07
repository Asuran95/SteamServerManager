
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import steamservermanager.SteamServerManager;
import steamservermanager.exceptions.ServerNameException;
import steamservermanager.exceptions.StartServerException;
import steamservermanager.interfaces.SteamServerManagerListener;
import steamservermanager.interfaces.serverrunner.ServerGameMessageReceiver;
import steamservermanager.interfaces.serverrunner.ServerMessageDispatcher;
import steamservermanager.interfaces.serverrunner.ServerProperties;
import steamservermanager.models.ServerGame;

public class Main {
    /**
     * Example
     */
    public static void main(String[] args) throws InterruptedException, ServerNameException, IOException, StartServerException {
        
        new SteamServerManagerProgram().start();

    }
}

class SteamServerManagerProgram{
    
    SteamServerManager steamServerManager;
            
    public void start() throws ServerNameException, StartServerException{
        steamServerManager = new SteamServerManager("/mnt/steamcompat/librarytest");
        
        steamServerManager.setListener(new SteamServerManagerListenerImpl());
        
        
    }
    
    
    class StandardOutputInterfaceImpl implements ServerGameMessageReceiver { 

        @Override
        public void onOutput(String msg) {
            System.out.println(msg);
        }
    }

    class SteamServerManagerListenerImpl implements SteamServerManagerListener {

        @Override
        public void onSteamCMDStdOut(String out) {
            System.out.println(out + "<<<<<");
        }

        @Override
        public void onUpdateServerStatus() {

        }

        @Override
        public void onReady() {  
            try {
//                steamServerManager.newServerGame(232250, "TF2Server", "./srcds_run -console -game tf +sv_pure 0 +map ctf_turbine +port 27666 +maxplayers 32");
                
                List<ServerGame> library = steamServerManager.getLibrary();

                ServerProperties serverProperties = steamServerManager.startServer(library.stream().findFirst().get());
                
                ServerMessageDispatcher listenerInput = serverProperties.setListener(new StandardOutputInterfaceImpl());
                
                while(true){
                    Scanner scanner = new Scanner(System.in);
                    
                    String command = scanner.nextLine();
                    
                    if(command.equals("stop")) {
                    	listenerInput.stop();
                    }
                    
                    listenerInput.send(command);
                }
                
            } catch (Exception ex) {
                Logger.getLogger(SteamServerManagerProgram.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }

        @Override
        public void onStatusSteamCMD(String status, double pctUpdate) {

        }

        @Override
        public void onUpdateServer(ServerGame serverGame) {

        }

        @Override
        public void onCompleteUpdateServer() {
            System.out.println("Atualização completa");
        }
    } 
    
    
}


