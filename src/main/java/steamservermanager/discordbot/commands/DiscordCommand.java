package steamservermanager.discordbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface DiscordCommand {
	
	void performAction(MessageReceivedEvent event, String[] commands);
	
	String help();

}
