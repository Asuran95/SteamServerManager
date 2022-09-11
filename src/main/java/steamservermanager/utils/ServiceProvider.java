package steamservermanager.utils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
	
	private final static Map<Class<?>, Object> servicesMap = new HashMap<>();
	
	private ServiceProvider() {}
	
	@SuppressWarnings("unchecked")
	public synchronized static <T> T provide(Class<T> classService) {
		
		try {
			if (servicesMap.containsKey(classService)) {
				return (T) servicesMap.get(classService);
				
			} else {
				Constructor<T> constructor = classService.getConstructor();
				
				T service = constructor.newInstance();
				
				servicesMap.put(classService, service);
				
				return service;
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
