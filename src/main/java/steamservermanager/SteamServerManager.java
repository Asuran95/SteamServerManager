package steamservermanager;

import java.util.ArrayList;
import java.util.List;

import steamservermanager.dtos.DiscordBotDTO;
import steamservermanager.dtos.ServerGameDTO;
import steamservermanager.eao.DiscordBotEAO;
import steamservermanager.eao.ManagerSettingsEAO;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.DiscordBot;
import steamservermanager.models.ManagerSettings;
import steamservermanager.models.ServerGame;
import steamservermanager.models.enums.ServerStatus;
import steamservermanager.serverrunner.interfaces.ServerProperties;
import steamservermanager.services.DiscordBotService;
import steamservermanager.services.SteamServerManagerService;
import steamservermanager.utils.ObjectUtils;
import steamservermanager.utils.ServiceProvider;
import steamservermanager.utils.SteamAPIUtils;

public class SteamServerManager {

    private SteamServerManagerService steamServerManagerService = ServiceProvider.provide(SteamServerManagerService.class);
    private ServerGameEAO serverGameEAO = ServiceProvider.provide(ServerGameEAO.class);

    public void startManager() {

    	List<ServerGame> serverGameLibrary = steamServerManagerService.getServerList();
    	
        for (ServerGame serverGame : serverGameLibrary) {
        	
        	if (serverGame.getManagerSettings() == null) {
            	ManagerSettingsEAO managerEAO = ServiceProvider.provide(ManagerSettingsEAO.class);
        		ManagerSettings manager = managerEAO.find(1L);
        		
        		serverGame.setManagerSettings(manager);
            }

            if (serverGame.getStatus().equals(ServerStatus.WAITING) || serverGame.getStatus().equals(ServerStatus.UPDATING)) {
            	steamServerManagerService.startUpdateServerGame(serverGame);

            } else {
                serverGame.setStatus(ServerStatus.STOPPED);
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
    
    public void update(ServerGameDTO serverGame) {
    	ServerGame serverGameLoaded = 
    			serverGameEAO.findServerGameById(serverGame.getIdServerGame());
    	
    	serverGameLoaded.setServerName(serverGame.getServerName());
    	serverGameLoaded.setDescription(serverGame.getDescription());
    	serverGameLoaded.setStartScript(serverGame.getStartScript());
    	
    	steamServerManagerService.update(serverGameLoaded);
    }

    public void startUpdateServerGame(ServerGameDTO serverGame) {
    	ServerGame serverGameLoaded = 
    			serverGameEAO.findServerGameById(serverGame.getIdServerGame());
    	
    	steamServerManagerService.startUpdateServerGame(serverGameLoaded);
    }

    public ServerProperties startServerGame(ServerGameDTO serverGame) {
    	ServerGame serverGameLoaded = serverGameEAO.findServerGameById(serverGame.getIdServerGame());
    	
        return steamServerManagerService.startServerGame(serverGameLoaded);
    }
    
    public void setDiscordBot(DiscordBotDTO discordBotDTO) {
    	DiscordBotService discordBotService = ServiceProvider.provide(DiscordBotService.class);
    	
    	DiscordBot discordBot = ObjectUtils.copyObject(discordBotDTO, DiscordBot.class);
    	
    	discordBotService.setDiscordBot(discordBot);
    }
    
    public DiscordBotDTO getDiscordBot() {
    	DiscordBotEAO discordBotEAO = ServiceProvider.provide(DiscordBotEAO.class);
    	
    	DiscordBot discordBot = discordBotEAO.find(1L);
    	
    	if (discordBot != null) {
    		return ObjectUtils.copyObject(discordBot, DiscordBotDTO.class);
    	}
    	
    	return null;	
    }
    
    public void startDiscordBot() {
    	DiscordBotService discordBotService = ServiceProvider.provide(DiscordBotService.class);
    	
    	discordBotService.start();
    }
    
    public void stopDiscordBot() {
    	DiscordBotService discordBotService = ServiceProvider.provide(DiscordBotService.class);
    	
    	discordBotService.stop();
    }
    
    public List<ServerGameDTO> getServerList() {

    	List<ServerGame> serverList = steamServerManagerService.getServerList();
    	
    	List<ServerGameDTO> copyList = new ArrayList<>();
    	
    	for (ServerGame serverGame : serverList) {
    		ServerGameDTO serverGameCopy = ObjectUtils.copyObject(serverGame, ServerGameDTO.class);
    		
    		copyList.add(serverGameCopy);
    	}
    	
        return copyList;
    } 
}
