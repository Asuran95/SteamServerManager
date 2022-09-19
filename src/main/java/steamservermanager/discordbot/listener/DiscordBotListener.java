package steamservermanager.discordbot.listener;

public interface DiscordBotListener {
	
	void onDiscordBotChangedStatus(String status);
	
	void onDiscordBotStarted();
	
	void onDiscordBotStopped();

}
