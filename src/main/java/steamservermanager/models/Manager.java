package steamservermanager.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manager {
	
	@Id
    private Long idManager;
	
	private String localLibrary;

	public Long getIdManager() {
		return idManager;
	}

	public void setIdManager(Long idManager) {
		this.idManager = idManager;
	}

	public String getLocalLibrary() {
		return localLibrary;
	}

	public void setLocalLibrary(String localLibrary) {
		this.localLibrary = localLibrary;
	}
}
