package steamservermanager.services;

import java.util.List;

import steamservermanager.eao.ManagerEAO;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.events.EventManagerService;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.interfaces.ServerProperties;
import steamservermanager.utils.ServiceProvider;
import steamservermanager.utils.StringUtils;
import steamservermanager.validators.SteamServerManagerValidator;

public class SteamServerManagerService {
	
	private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);
	private UpdaterServerGameService updaterServerGameService = ServiceProvider.provide(UpdaterServerGameService.class);
	private ServerRunnerService serverRunnerService = ServiceProvider.provide(ServerRunnerService.class);
    private SteamServerManagerValidator validator = new SteamServerManagerValidator();
    private EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);
    
    public ServerGame create(ServerGame serverGame) {
    	String localNameNormalized = StringUtils.normalizeStringForDirectoryName(serverGame.getLocalName());
    	
    	ManagerEAO managerEAO = ServiceProvider.provide(ManagerEAO.class);
    	
    	serverGame.setLocalName(localNameNormalized);
    	serverGame.setManager(managerEAO.find(1L));
    	
    	validator.validadeNewServer(serverGame);
        serverGameEAO.persist(serverGame);
        
    	updaterServerGameService.update(serverGame);
    	
    	return serverGame;
    }
    
    public ServerGame update(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	for(SteamServerManagerListener steamServerManagerListener : eventManager.getSteamServerManagerListeners()) {
    		steamServerManagerListener.onServerGameChanged();
    	}

    	return serverGame;
    }
    
    public void startUpdateServerGame(ServerGame serverGame) {
    	serverRunnerService.stopServer(serverGame);
    	updaterServerGameService.update(serverGame);
    }
    
    public ServerProperties startServerGame(ServerGame serverGame) {
        return serverRunnerService.startServer(serverGame);
    }
    
    public List<ServerGame> getServerList() {
    	List<ServerGame> serverList = serverGameEAO.findAllServerGame();
    	
    	return serverList;
    }

}
