package steamservermanager;

import java.util.ArrayList;
import java.util.List;

public class UpdateMonitor {

	private List<ServerGame> updateList = new ArrayList<>();
	private LibraryFileHelper libraryHelper;
	
	
	public UpdateMonitor(LibraryFileHelper libraryHelper) {
		this.libraryHelper = libraryHelper;
	}
	
	public void addUpdate(ServerGame serverGame) {
		
		serverGame.setStatus(Status.WAITING);
		
		updateList.add(serverGame);
		
		libraryHelper.updateLibraryFile();
		
		synchronized (this) {
			notify();
		}
	}
	
	public ServerGame getUpdateJob() {
		
		if(updateList.size() == 0) {
			try {
				System.out.println("Aguardando");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return updateList.get(0);
	}
	
	public void completedUpdateJob(ServerGame serverGame) {	
		
		serverGame.setStatus(Status.STOPPED);
		
		updateList.remove(serverGame);
		libraryHelper.updateLibraryFile();
	}
}
