package steamservermanager;

import steamservermanager.eao.EntityManagerSingleton;
import steamservermanager.eao.ManagerSettingsEAO;
import steamservermanager.events.EventManagerService;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.models.ManagerSettings;
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
		
		createOrUpdateManagerSettings(localLibrary);
		
		EventManagerService eventManager = ServiceProvider.provide(EventManagerService.class);
		eventManager.addListener(listener);
		
		return new SteamServerManager();
	}
	
	private void setupEntityManager(String localLibrary) {
		EntityManagerSingleton.init(localLibrary);
	}
	
	private void createOrUpdateManagerSettings(String localLibrary) {
		ManagerSettingsEAO managerSettingsEAO = ServiceProvider.provide(ManagerSettingsEAO.class);
		
		ManagerSettings manager = managerSettingsEAO.find(1L);
		
		if (manager == null) {
			manager = new ManagerSettings();
			manager.setIdManager(1L);
			manager.setLocalLibrary(localLibrary);
			
			managerSettingsEAO.persist(manager);
		} else {
			manager.setLocalLibrary(localLibrary);
			
			managerSettingsEAO.merge(manager);
		}
	}
}
