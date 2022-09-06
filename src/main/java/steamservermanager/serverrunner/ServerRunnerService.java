package steamservermanager.serverrunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import steamservermanager.events.EventManager;
import steamservermanager.models.ServerGame;
import steamservermanager.models.enums.Status;
import steamservermanager.serverrunner.interfaces.ServerProperties;

public class ServerRunnerService {
	
	private List<ServerRunner> serversRunning = new ArrayList<>();
	private String localLibrary;
	private EventManager eventManager;
	
	
	public ServerRunnerService(String localLibrary, EventManager eventManager) {
		this.localLibrary = localLibrary;
		this.eventManager = eventManager;
	}

	public ServerProperties startServer(ServerGame serverGame) {
		ServerProperties serverProperties = null;
	       
    	Optional<ServerRunner> serverRunnerOptional = findServerRunner(serverGame);
    	
    	if (serverRunnerOptional.isPresent()) {
    		
    		ServerRunner serverRunner = serverRunnerOptional.get();
    		
    		if (serverRunner.isRunning() && serverGame.getStatus().equals(Status.RUNNING)) {
    			serverProperties = serverRunner.getServerProperties();
    			
    		} else {
    			serversRunning.remove(serverRunner);
    			
    			serverRunner = new ServerRunner(serverGame, localLibrary, eventManager.getServerRunnerListener());
    			
    			serversRunning.add(serverRunner);
    			
    			serverRunner.start();
    			
    			serverProperties = serverRunner.getServerProperties();
    		}
    		
    	} else {
    		ServerRunner serverRunner = new ServerRunner(serverGame, localLibrary, eventManager.getServerRunnerListener());
    		
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
