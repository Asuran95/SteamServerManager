package steamservermanager.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DiscordBot {
	
	@Id
    private Long idDiscordBot;
	
	private String token;
	
	private String prefix;
	
	private Long ownerUserId;

	public Long getIdDiscordBot() {
		return idDiscordBot;
	}

	public void setIdDiscordBot(Long idDiscordBot) {
		this.idDiscordBot = idDiscordBot;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	
	
}
