package steamservermanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import steamservermanager.events.EventManagerService;
import steamservermanager.models.ServerGame;
import steamservermanager.models.enums.Status;
import steamservermanager.serverrunner.ServerRunner;
import steamservermanager.serverrunner.interfaces.ServerProperties;
import steamservermanager.utils.ServiceProvider;

public class ServerRunnerService {
	
	private List<ServerRunner> serversRunning = new ArrayList<>();
	private EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);

	public ServerProperties startServer(ServerGame serverGame) {
		ServerProperties serverProperties = null;
	       
    	Optional<ServerRunner> serverRunnerOptional = findServerRunner(serverGame);
    	
    	if (serverRunnerOptional.isPresent()) {
    		
    		ServerRunner serverRunner = serverRunnerOptional.get();
    		
    		if (serverRunner.isRunning() && serverGame.getStatus().equals(Status.RUNNING)) {
    			serverProperties = serverRunner.getServerProperties();
    			
    		} else {
    			serversRunning.remove(serverRunner);
    			
    			serverRunner = new ServerRunner(serverGame, eventManager.getServerRunnerListener());
    			
    			serversRunning.add(serverRunner);
    			
    			serverRunner.start();
    			
    			serverProperties = serverRunner.getServerProperties();
    		}
    		
    	} else {
    		ServerRunner serverRunner = new ServerRunner(serverGame, eventManager.getServerRunnerListener());
    		
    		serversRunning.add(serverRunner);
			
			serverRunner.start();
			
			serverProperties = serverRunner.getServerProperties();
    	}
    	
    	return serverProperties;
	}
	
	public void stopServer(ServerGame serverGame) {
		for (ServerRunner serverRunner : serversRunning){
            if(serverRunner.getServerGame().equals(serverGame)){
            	if (serverRunner.isRunning()) {
            		serversRunning.remove(serverRunner);
            		serverRunner.forceStop();
            		
            		break;
            		
            	} else {
            		serversRunning.remove(serverRunner);
            		
            		break;
            	} 
            }
        }
	}
	
	private Optional<ServerRunner> findServerRunner(ServerGame serverGame) {
    	for(ServerRunner serverRunner : serversRunning) {
    		
    		if(serverRunner.getServerGame().equals(serverGame)) {
    			return Optional.of(serverRunner);
    		}
    	}
    	
    	return Optional.empty();
    }
	
	

}
