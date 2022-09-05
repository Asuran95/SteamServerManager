package steamservermanager;

import steamservermanager.eao.SteamServerManagerEAO;
import steamservermanager.exceptions.ServerLocalNameDuplicatedException;
import steamservermanager.models.ServerGame;

public class SteamServerManagerValidator {
	
	private SteamServerManagerEAO steamServerManagerEAO;
	
	public SteamServerManagerValidator(SteamServerManagerEAO steamServerManagerEAO) {
		this.steamServerManagerEAO = steamServerManagerEAO;
	}

	public void validadeNewServer(ServerGame serverGame) {
		validadeLocalServerName(serverGame);	
	}
	
	public void validateUpdateServer(ServerGame serverGame) {
		
	}
	
	private void validadeLocalServerName(ServerGame serverGame) {
		ServerGame serverGameLoaded = 
				steamServerManagerEAO.findServerGameByLocalName(serverGame.getLocalName());
    			
		if (serverGameLoaded != null) {
			throw new ServerLocalNameDuplicatedException();
		}
	}

}
