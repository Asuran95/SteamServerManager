package steamservermanager.discordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface DiscordCommand {
	
	void performAction(SlashCommandInteractionEvent event);
	
	String help();

}
