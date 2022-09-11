package steamservermanager;

import steamservermanager.eao.EntityManagerSingleton;
import steamservermanager.eao.ManagerEAO;
import steamservermanager.events.EventManagerService;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.Manager;
import steamservermanager.utils.ServiceProvider;

public class SteamServerManagerBuilder {
	
	private String localLibrary;
	private SteamServerManagerListener listener;
	
	public SteamServerManagerBuilder setLocalLibrary(String localLibrary) {
		this.localLibrary = localLibrary;
		
		return this;
	}
	
	public SteamServerManagerBuilder setListener(SteamServerManagerListener listener){
		this.listener = listener;
		 
		return this;
	}
	
	public SteamServerManager build() {
		setupEntityManager(localLibrary);
		
		createOrUpdateManager(localLibrary);
		
		EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);
		eventManager.addListener(listener);
		
		return new SteamServerManager();
	}
	
	private void setupEntityManager(String localLibrary) {
		EntityManagerSingleton.init(localLibrary);
	}
	
	private void createOrUpdateManager(String localLibrary) {
		ManagerEAO managerEAO = ServiceProvider.provide(ManagerEAO.class);
		
		Manager manager = managerEAO.find(1L);
		
		if (manager == null) {
			manager = new Manager();
			manager.setIdManager(1L);
			manager.setLocalLibrary(localLibrary);
			
			managerEAO.persist(manager);
		} else {
			manager.setLocalLibrary(localLibrary);
			
			managerEAO.merge(manager);
		}
	}
}
