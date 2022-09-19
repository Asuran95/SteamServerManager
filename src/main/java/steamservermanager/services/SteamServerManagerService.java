package steamservermanager.services;

import java.util.List;

import steamservermanager.dtos.ServerGameDTO;
import steamservermanager.eao.ManagerSettingsEAO;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.events.EventManagerService;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ServerGame;
import steamservermanager.serverrunner.interfaces.ServerProperties;
import steamservermanager.utils.ObjectUtils;
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
    	
    	ManagerSettingsEAO managerEAO = ServiceProvider.provide(ManagerSettingsEAO.class);
    	
    	serverGame.setLocalName(localNameNormalized);
    	serverGame.setManagerSettings(managerEAO.find(1L));
    	
    	validator.validadeNewServer(serverGame);
        serverGameEAO.persist(serverGame);
        
    	updaterServerGameService.update(serverGame);
    	
    	return serverGame;
    }
    
    public ServerGame update(ServerGame serverGame) {
    	serverGameEAO.merge(serverGame);
    	
    	ServerGameDTO serverGameDTO = ObjectUtils.copyObject(serverGame, ServerGameDTO.class);
    	
    	for(SteamServerManagerListener steamServerManagerListener : eventManager.getSteamServerManagerListeners()) {
    		steamServerManagerListener.onServerGameChanged(serverGameDTO);
    	}

    	return serverGame;
    }
    
    public void startUpdateServerGame(ServerGame serverGame) {
    	updaterServerGameService.update(serverGame);
    }
    
    public ServerProperties startServerGame(ServerGame serverGame) {
        return serverRunnerService.startServer(serverGame);
    }
    
    public List<ServerGame> getServerList() {
    	List<ServerGame> serverList = serverGameEAO.findAll();
    	
    	return serverList;
    }

}
