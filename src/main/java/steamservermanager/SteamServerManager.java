package steamservermanager;

import java.util.List;

import steamservermanager.exceptions.ServerNameException;

public class SteamServerManager {
	
	private List<ServerGame> library = null;
	private String localLibrary;
	
	private UpdateThread updateThread;
	private UpdateMonitor updateMonitor;
	private LibraryFileHelper libraryHelper;
	

	public SteamServerManager(String localLibrary) {
		this.localLibrary = localLibrary;
		libraryHelper = new LibraryFileHelper(localLibrary);
		library = libraryHelper.loadLibraryFromDisk();
		
		init();
	}
	
	private void init() {
		updateMonitor = new UpdateMonitor(libraryHelper);
		
		updateThread = new UpdateThread(updateMonitor, localLibrary);
		updateThread.start();
		
		//Verificar aqui se ainda possui algum servergame com status de waiting
		
		for(ServerGame l : library) {
			
			if(l.getStatus().equals(Status.WAITING) || l.getStatus().equals(Status.UPDATING)) {
				updateMonitor.addUpdate(l);
			} else {
				l.setStatus(Status.STOPPED);
			}
		}
	}
	
	
	public void newServerGame(ServerGame serverGame) throws ServerNameException {
		
		for(ServerGame s : library) {
			
			if(s.getServerName().equals(serverGame.getServerName())) {
				throw new ServerNameException();
			}
		}
		
		library.add(serverGame);
		
		updateServerGame(serverGame);
		
	}
	
	public void updateServerGame(ServerGame serverGame) {
		
		//obs: Aqui deve colocar tambem um modo de parar o servidor caso esse comando seja chamado
		
		updateMonitor.addUpdate(serverGame);
		
	}
	
	public void startServer(ServerGame serverGame) {
		
	}

	public List<ServerGame> getLibrary() {
		return library;
	}

	public void setLibrary(List<ServerGame> library) {
		this.library = library;
	}

	public String getLocalLibrary() {
		return localLibrary;
	}

	public void setLocalLibrary(String localLibrary) {
		this.localLibrary = localLibrary;
	}	
}
