package steamservermanager.dtos;

public class DiscordBotDTO {
	
	private String token;
	
	private Long ownerUserId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
}
