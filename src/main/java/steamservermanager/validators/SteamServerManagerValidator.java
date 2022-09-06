package steamservermanager.validators;

import steamservermanager.eao.SteamServerManagerEAO;
import steamservermanager.exceptions.ServerLocalNameDuplicatedException;
import steamservermanager.exceptions.ServerLocalNameIsEmptyException;
import steamservermanager.exceptions.SteamIDNotFoundException;
import steamservermanager.models.ServerGame;
import steamservermanager.utils.SteamAPIUtils;

public class SteamServerManagerValidator {
	
	private SteamServerManagerEAO steamServerManagerEAO;
	
	public SteamServerManagerValidator(SteamServerManagerEAO steamServerManagerEAO) {
		this.steamServerManagerEAO = steamServerManagerEAO;
	}

	public void validadeNewServer(ServerGame serverGame) {
		
		validadeLocalServerName(serverGame);	
		validadeServerAppID(serverGame);
	}
	
	public void validateUpdateServer(ServerGame serverGame) {
		
	}
	
	private void validadeLocalServerName(ServerGame serverGame) {
		String localName = serverGame.getLocalName();
		
		if (localName == null || localName.isBlank() || localName.isEmpty()) {
			throw new ServerLocalNameIsEmptyException();
		}

		ServerGame serverGameLoaded = 
				steamServerManagerEAO.findServerGameByLocalName(localName);
    			
		if (serverGameLoaded != null) {
			throw new ServerLocalNameDuplicatedException();
		}
	}
	
	private void validadeServerAppID(ServerGame serverGame) {
		
		String gameName = SteamAPIUtils.getGameNameBySteamId(serverGame.getAppID());
		
		if (gameName == null) {
			throw new SteamIDNotFoundException();
    	}
	}

}
