package steamservermanager.discordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class DiscordCommandValidator {
	
	public void validateEvent(SlashCommandInteractionEvent event) {}
	
}
