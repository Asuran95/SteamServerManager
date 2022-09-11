package steamservermanager.eao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {
	
	private static EntityManager entityManager;
	
	private EntityManagerSingleton() {}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
	
	public static void init(String localLibrary) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.connection.url", "jdbc:sqlite:" + localLibrary + File.separator + "SteamServerManager.db");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SteamServerManager", properties);
		entityManager = entityManagerFactory.createEntityManager();
	}
}
