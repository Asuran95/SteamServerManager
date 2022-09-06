package steamservermanager;

import java.util.ArrayList;
import java.util.List;

import steamservermanager.eao.SteamServerManagerEAO;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.models.enums.Status;
import steamservermanager.serverrunner.ServerRunnerService;
import steamservermanager.serverrunner.interfaces.ServerProperties;
import steamservermanager.updaterservergame.UpdaterServerGameService;
import steamservermanager.utils.ObjectUtils;
import steamservermanager.utils.SteamAPIUtils;
import steamservermanager.validators.SteamServerManagerValidator;
import steamservermanager.vos.ServerGameVO;

public class SteamServerManager {

    private UpdaterServerGameService updaterServerGameService;
    private ServerRunnerService serverRunnerService;
    private SteamServerManagerEAO steamServerManagerEAO;
    private SteamServerManagerValidator validator;
    private SteamServerManagerListener listener;


    public SteamServerManager(UpdaterServerGameService updaterServerGameService, ServerRunnerService serverRunnerService, SteamServerManagerEAO steamServerManagerEAO, SteamServerManagerListener listener) {
        this.updaterServerGameService = updaterServerGameService;
        this.serverRunnerService = serverRunnerService;
        this.steamServerManagerEAO = steamServerManagerEAO;
        this.validator = new SteamServerManagerValidator(steamServerManagerEAO);
        this.listener = listener;
    }

    public void startManager() {

    	List<ServerGame> serverGameLibrary = steamServerManagerEAO.findAllServerGame();
    	
        for (ServerGame serverGame : serverGameLibrary) {

            if (serverGame.getStatus().equals(Status.WAITING) || serverGame.getStatus().equals(Status.UPDATING)) {
            	updaterServerGameService.update(serverGame);

            } else {
                serverGame.setStatus(Status.STOPPED);
            }
        }
    }

    public void create(int appId, String localName, String serverName, String startScript) {
    	
    	localName = normalizeStringForDirectoryName(localName);
    	
        ServerGame serverGame = new ServerGame();
        serverGame.setAppID(appId);
        serverGame.setGameName(SteamAPIUtils.getGameNameBySteamId(appId));
        serverGame.setLocalName(localName);
        serverGame.setStartScript(startScript);
        
        validator.validadeNewServer(serverGame);
        
        steamServerManagerEAO.persistServerGame(serverGame);
        
    	updaterServerGameService.update(serverGame);
    }
    
    private String normalizeStringForDirectoryName(String dirName) {
    	dirName.trim();
    	dirName = dirName.replaceAll(" ", "_");
    	dirName = dirName.replaceAll("\\W+", "").trim();

    	return dirName;
    }
    
    public void save(ServerGameVO serverGame) {
    	ServerGame serverGameLoaded = 
    			steamServerManagerEAO.findServerGameById(serverGame.getIdServerGame());
    	
    	serverGameLoaded.setServerName(serverGame.getServerName());
    	serverGameLoaded.setDescription(serverGame.getDescription());
    	serverGameLoaded.setStartScript(serverGame.getStartScript());
    	
    	steamServerManagerEAO.mergeServerGame(serverGameLoaded);
    	
    	listener.onServerGameChanged();
    }

    public void update(ServerGameVO serverGame) {
    	ServerGame serverGameLoaded = 
    			steamServerManagerEAO.findServerGameById(serverGame.getIdServerGame());
    	
    	serverRunnerService.stopServer(serverGameLoaded);
    	updaterServerGameService.update(serverGameLoaded);
    }

    public ServerProperties start(ServerGameVO serverGame) {
    	ServerGame serverGameLoaded = steamServerManagerEAO.findServerGameById(serverGame.getIdServerGame());
    	
        return serverRunnerService.startServer(serverGameLoaded);
    }
    
    public List<ServerGameVO> getServerList() {

    	List<ServerGame> serverList = steamServerManagerEAO.findAllServerGame();
    	
    	List<ServerGameVO> copyList = new ArrayList<>();
    	
    	for (ServerGame serverGame : serverList) {
    		ServerGameVO serverGameCopy = ObjectUtils.copyObject(serverGame, ServerGameVO.class);
    		
    		copyList.add(serverGameCopy);
    	}
    	
        return copyList;
    } 
}
