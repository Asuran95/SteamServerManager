package steamservermanager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import steamservermanager.eao.SteamServerManagerEAO;
import steamservermanager.events.EventManager;
import steamservermanager.listeners.SteamServerManagerListener;
import steamservermanager.serverrunner.ServerRunnerService;
import steamservermanager.updaterservergame.UpdaterServerGameService;

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
		SteamServerManagerEAO steamServerManagerEAO = setupSteamServerManagerEAO(localLibrary);

		EventManager eventManager = new EventManager(steamServerManagerEAO, listener);
		
		UpdaterServerGameService updaterServerGameService = new UpdaterServerGameService(localLibrary, eventManager);
		ServerRunnerService serverRunnerService = new ServerRunnerService(localLibrary, eventManager);

		return new SteamServerManager(updaterServerGameService, serverRunnerService, steamServerManagerEAO, listener);
	}
	
	private SteamServerManagerEAO setupSteamServerManagerEAO(String localLibrary) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.connection.url", "jdbc:sqlite:" + localLibrary + File.separator + "SteamServerManager.db");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SteamServerManager", properties);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		return new SteamServerManagerEAO(entityManager);
	}
}
