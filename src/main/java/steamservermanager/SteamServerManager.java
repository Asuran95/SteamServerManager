package steamservermanager;

import java.util.List;

import steamservermanager.exceptions.ServerNameException;
import steamservermanager.exceptions.StartServerException;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.ServerRunnerService;
import steamservermanager.serverrunner.interfaces.ServerProperties;
import steamservermanager.updaterservergame.UpdaterServerGameService;
import steamservermanager.utils.Status;

public class SteamServerManager {

    private List<ServerGame> serverGameLibrary = null;
    private UpdaterServerGameService updaterServerGameService;
    private ServerRunnerService serverRunnerService;


    public SteamServerManager(List<ServerGame> serverGameLibrary, UpdaterServerGameService updaterServerGameService, ServerRunnerService serverRunnerService) {
        this.serverGameLibrary = serverGameLibrary;
        this.updaterServerGameService = updaterServerGameService;
        this.serverRunnerService = serverRunnerService;
    }

    public void start() {

        for (ServerGame serverGame : serverGameLibrary) {

            if (serverGame.getStatus().equals(Status.WAITING) || serverGame.getStatus().equals(Status.UPDATING)) {
                updateServerGame(serverGame);
            } else {
                serverGame.setStatus(Status.STOPPED);
            }
        }
    }

    public ServerGame newServerGame(int gameId, String serverName, String startScript) throws ServerNameException {

        for (ServerGame s : serverGameLibrary) {

            if (s.getServerName().equals(serverName)) {
                throw new ServerNameException();
            }
        }
        
        ServerGame serverGame = new ServerGame(gameId, serverName, startScript);
        
        serverGameLibrary.add(serverGame);
        
        updateServerGame(serverGame);
        
        return serverGame;
    }

    public void updateServerGame(ServerGame serverGame) {
    	updaterServerGameService.update(serverGame);
    }

    public ServerProperties startServer(ServerGame serverGame) throws StartServerException {
        return serverRunnerService.startServer(serverGame);
    }

    public List<ServerGame> getLibrary() {
        return serverGameLibrary;
    } 
}
