package steamservermanager.discordbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class DiscordCommandValidator {
	
	public void validateEvent(MessageReceivedEvent event) {}
	
}
