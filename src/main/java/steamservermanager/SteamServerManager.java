package steamservermanager;

import steamservermanager.models.ServerGame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import steamcmd.SteamCMD;
import steamcmd.SteamCMDListener;

import steamservermanager.exceptions.ServerNameException;
import steamservermanager.exceptions.ServerNotRunningException;
import steamservermanager.exceptions.StartServerException;
import steamservermanager.interfaces.SteamServerManagerListener;
import steamservermanager.interfaces.UpdateMonitorListener;
import steamservermanager.models.ServerGameViewer;
import steamservermanager.interfaces.ServerProperties;
import steamservermanager.interfaces.ServerRunnerListener;

public class SteamServerManager {

    private List<ServerGame> library = null;
    private List<ServerRunner> libraryRunning = new ArrayList<>();
       
    private String localLibrary;

    private UpdateThread updateThread;
    private UpdateMonitor updateMonitor;
    private final LibraryFileHelper libraryHelper;
    private SteamCMD steamCmd;
    
    private SteamServerManagerListener listener;

    public SteamServerManager(String localLibrary) {
        this.localLibrary = localLibrary;
        libraryHelper = new LibraryFileHelper(localLibrary);
        library = libraryHelper.loadLibraryFromDisk();
        this.listener = new ManagerListenerDummy();
        
        new Thread(){
            @Override
            public void run() {
                init();
            }  
        }.start();
    }

    private void init() {
        updateMonitor = new UpdateMonitor(new UpdateMonitorListenerImpl());

        try {
            steamCmd = new SteamCMD(new ConsoleSteamCmdListener());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        updateThread = new UpdateThread(updateMonitor, localLibrary, steamCmd);
        updateThread.start();

        //Verificar aqui se ainda possui algum servergame com status de waiting
        for (ServerGame l : library) {

            if (l.getStatus().equals(Status.WAITING) || l.getStatus().equals(Status.UPDATING)) {
                updateMonitor.addUpdate(l);
            } else {
                l.setStatus(Status.STOPPED);
            }
        }
        listener.onReady();
    }

    public void newServerGame(int gameId, String serverName, String startScript) throws ServerNameException {

        for (ServerGame s : library) {

            if (s.getServerName().equals(serverName)) {
                throw new ServerNameException();
            }
        }
        
        ServerGame serverGame = new ServerGame(gameId, serverName, startScript);
        
        library.add(serverGame);

        updateServerGame(serverGame);
    }

    public void updateServerGame(ServerGame serverGame) {

        //obs: Aqui deve colocar tambem um modo de parar o servidor caso esse comando seja chamado
        updateMonitor.addUpdate(serverGame);
    }

    public ServerProperties startServer(ServerGameViewer serverGame) throws StartServerException {
        return startServer(serverGame.getId());
    }
    
    public ServerProperties startServer(String id) throws StartServerException{
        
        ServerProperties serverProperties = null;
        
        for(ServerGame server : library){
            
            if(server.getId().equals(id)){
                
                ServerRunner runner = new ServerRunner(server, localLibrary, new ServerRunnerListenerImpl());
                
                runner.start();
                
                serverProperties = runner.getServerProperties(); 
                libraryRunning.add(runner);
                
                break;
            }
        }
        
        if(serverProperties == null){
            throw new StartServerException();
        }
             
        return serverProperties;     
    }
    
    public ServerProperties getServerProperties(String id) throws ServerNotRunningException {
        
        for(ServerRunner runner : libraryRunning){
            
            if(runner.getServerGame().getId().equals(id)){
                
                return runner.getServerProperties();     
            }    
        }
        throw new ServerNotRunningException();     
    }
    
    public List<ServerGameViewer> getLibraryList(){
        
        List<ServerGameViewer> libraryViewer = new ArrayList<>();
        
        for(ServerGame serverGame : library){
            libraryViewer.add(new ServerGameViewer(serverGame));  
        }
        return libraryViewer;
    }

    public List<ServerGame> getLibrary() {
        return library;
    }

    public void setLibrary(List<ServerGame> library) {
        this.library = library;
    }

    public String getLocalLibrary() {
        return localLibrary;
    }

    public void setLocalLibrary(String localLibrary) {
        this.localLibrary = localLibrary;
    }
    
    public void setListener(SteamServerManagerListener listener){
        this.listener = listener;
    }
    
    class ServerRunnerListenerImpl implements ServerRunnerListener{

        @Override
        public void onServerStart(ServerGame serverGame) {
            libraryHelper.updateLibraryFile();
            listener.onUpdateServerStatus();
        }

        @Override
        public void onServerStopped(ServerGame serverGame) {
            libraryHelper.updateLibraryFile();
            listener.onUpdateServerStatus();
        }

        @Override
        public void onServerException(ServerGame serverGame) {
            
        }     
    }
    
    class UpdateMonitorListenerImpl implements UpdateMonitorListener {

        @Override
        public void onNewUpdate(ServerGame server) {
            libraryHelper.updateLibraryFile();
            listener.onUpdateServerStatus();
        }

        @Override
        public void onGetUpdateJob(ServerGame server) {
            libraryHelper.updateLibraryFile();
            listener.onUpdateServerStatus();
            listener.onUpdateServer(new ServerGameViewer(server));
        }

        @Override
        public void onCompleteJob(ServerGame server) {
            libraryHelper.updateLibraryFile();
            listener.onUpdateServerStatus();
            listener.onCompleteUpdateServer();
        }
        
    }

    class ConsoleSteamCmdListener implements SteamCMDListener {

        @Override
        public void onStdOut(String out) {
            listener.onSteamCMDStdOut(out);
            
            if(out.contains("verifying") || out.contains("downloading")){
                
                String[] splitOut = out.split(":");
                
                String[] pctStringSplit = splitOut[1].split(" ");
                
                String[] statusStringSplit = splitOut[0].split(" ");
                
                double pct = Double.parseDouble(pctStringSplit[1]);
                
                listener.onStatusSteamCMD(statusStringSplit[4].replace(",", ""), pct);
            }
        }

        @Override
        public String onAuthCode() {

            System.out.print("Two-factor code:");
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
    
    class ManagerListenerDummy implements SteamServerManagerListener {

        @Override
        public void onUpdateServerStatus() {
            System.out.println("Status server updated");
        }

        @Override
        public void onSteamCMDStdOut(String out) {
            //System.out.println(out);
        }

        @Override
        public void onReady() {
            System.out.println("Ready!!");
        }

        @Override
        public void onStatusSteamCMD(String status, double pctUpdate) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUpdateServer(ServerGameViewer serverGame) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onCompleteUpdateServer() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
