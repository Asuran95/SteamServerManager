package steamservermanager;

import java.util.ArrayList;
import java.util.List;

import steamservermanager.eao.ManagerEAO;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.Manager;
import steamservermanager.models.ServerGame;
import steamservermanager.models.enums.Status;
import steamservermanager.serverrunner.interfaces.ServerProperties;
import steamservermanager.services.SteamServerManagerService;
import steamservermanager.utils.ObjectUtils;
import steamservermanager.utils.ServiceProvider;
import steamservermanager.utils.SteamAPIUtils;
import steamservermanager.vos.ServerGameVO;

public class SteamServerManager {

    private SteamServerManagerService steamServerManagerService = ServiceProvider.provide(SteamServerManagerService.class);
    private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);

    public void startManager() {

    	List<ServerGame> serverGameLibrary = steamServerManagerService.getServerList();
    	
        for (ServerGame serverGame : serverGameLibrary) {
        	
        	if (serverGame.getManager() == null) {
            	ManagerEAO managerEAO = ServiceProvider.provide(ManagerEAO.class);
        		Manager manager = managerEAO.find(1L);
        		
        		serverGame.setManager(manager);
            }

            if (serverGame.getStatus().equals(Status.WAITING) || serverGame.getStatus().equals(Status.UPDATING)) {
            	steamServerManagerService.startUpdateServerGame(serverGame);

            } else {
                serverGame.setStatus(Status.STOPPED);
            }
        }
    }

    public void create(int appId, String localName, String serverName, String startScript) {
        ServerGame serverGame = new ServerGame();
        serverGame.setAppID(appId);
        serverGame.setGameName(SteamAPIUtils.getGameNameBySteamId(appId));
        serverGame.setLocalName(localName);
        serverGame.setServerName(serverName);
        serverGame.setStartScript(startScript);
        
        steamServerManagerService.create(serverGame);
    }
    
    public void update(ServerGameVO serverGame) {
    	ServerGame serverGameLoaded = 
    			serverGameEAO.findServerGameById(serverGame.getIdServerGame());
    	
    	serverGameLoaded.setServerName(serverGame.getServerName());
    	serverGameLoaded.setDescription(serverGame.getDescription());
    	serverGameLoaded.setStartScript(serverGame.getStartScript());
    	
    	steamServerManagerService.update(serverGameLoaded);
    }

    public void startUpdateServerGame(ServerGameVO serverGame) {
    	ServerGame serverGameLoaded = 
    			serverGameEAO.findServerGameById(serverGame.getIdServerGame());
    	
    	steamServerManagerService.startUpdateServerGame(serverGameLoaded);
    }

    public ServerProperties startServerGame(ServerGameVO serverGame) {
    	ServerGame serverGameLoaded = serverGameEAO.findServerGameById(serverGame.getIdServerGame());
    	
        return steamServerManagerService.startServerGame(serverGameLoaded);
    }
    
    public List<ServerGameVO> getServerList() {

    	List<ServerGame> serverList = steamServerManagerService.getServerList();
    	
    	List<ServerGameVO> copyList = new ArrayList<>();
    	
    	for (ServerGame serverGame : serverList) {
    		ServerGameVO serverGameCopy = ObjectUtils.copyObject(serverGame, ServerGameVO.class);
    		
    		copyList.add(serverGameCopy);
    	}
    	
        return copyList;
    } 
}
