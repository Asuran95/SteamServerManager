package steamservermanager;

import steamservermanager.models.ServerGame;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import steamcmd.SteamCMD;
import steamcmd.SteamCMDListener;

import steamservermanager.exceptions.ServerNameException;

public class SteamServerManager {

    private List<ServerGame> library = null;
    private String localLibrary;

    private UpdateThread updateThread;
    private UpdateMonitor updateMonitor;
    private final LibraryFileHelper libraryHelper;
    private SteamCMD steamCmd;
    private SteamCMDListener steamCmdListener;

    public SteamServerManager(String localLibrary) {
        this.localLibrary = localLibrary;
        libraryHelper = new LibraryFileHelper(localLibrary);
        library = libraryHelper.loadLibraryFromDisk();
        init();
    }

    public SteamServerManager(String localLibrary, SteamCMDListener steamCmdListener) {
        this.steamCmdListener = steamCmdListener;
        this.localLibrary = localLibrary;
        libraryHelper = new LibraryFileHelper(localLibrary);
        library = libraryHelper.loadLibraryFromDisk();
        init();
    }

    private void init() {
        updateMonitor = new UpdateMonitor(libraryHelper);

        if (steamCmdListener == null) {
            steamCmdListener = new ConsoleSteamCmdListener();
        }

        try {
            steamCmd = new SteamCMD(steamCmdListener);
        } catch (IOException ex) {
            Logger.getLogger(SteamServerManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SteamServerManager.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    public void newServerGame(ServerGame serverGame) throws ServerNameException {

        for (ServerGame s : library) {

            if (s.getServerName().equals(serverGame.getServerName())) {
                throw new ServerNameException();
            }
        }

        library.add(serverGame);

        updateServerGame(serverGame);

    }

    public void updateServerGame(ServerGame serverGame) {

        //obs: Aqui deve colocar tambem um modo de parar o servidor caso esse comando seja chamado
        updateMonitor.addUpdate(serverGame);

    }

    public void startServer(ServerGame serverGame) {

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

    class ConsoleSteamCmdListener implements SteamCMDListener {

        @Override
        public void onStdOut(String out) {
            System.out.println(out);
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
}
