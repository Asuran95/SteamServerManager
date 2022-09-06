package steamservermanager.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import steamservermanager.models.enums.Status;

@Entity
public class ServerGame {
	
	@Id
	@GeneratedValue
    private Long idServerGame;
    
    private Integer appID;
    
    private String localName;
    
    private String serverName;
    
    private String description;
    
    private String startScript;
    
    private String gameName;
    
    @Enumerated(EnumType.STRING)
    private Status status;

	public Long getIdServerGame() {
		return idServerGame;
	}

	public void setIdServerGame(Long idServerGame) {
		this.idServerGame = idServerGame;
	}

	public Integer getAppID() {
		return appID;
	}

	public void setAppID(Integer appID) {
		this.appID = appID;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartScript() {
		return startScript;
	}

	public void setStartScript(String startScript) {
		this.startScript = startScript;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
