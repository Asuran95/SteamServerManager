package steamservermanager.discordbot.commands.startserver;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import steamservermanager.discordbot.commands.DiscordCommandValidator;
import steamservermanager.eao.DiscordBotEAO;
import steamservermanager.models.DiscordBot;
import steamservermanager.utils.ServiceProvider;

public class StartServerCommandValidator extends DiscordCommandValidator {
	
	@Override
	public void validateEvent(SlashCommandInteractionEvent event) {
		
		DiscordBotEAO discordBotEAO = ServiceProvider.provide(DiscordBotEAO.class);
		
		DiscordBot discordBot = discordBotEAO.find(1L);
		
		Long idLong = event.getUser().getIdLong();
		
		if (!idLong.equals(discordBot.getOwnerUserId())) {
			throw new RuntimeException("Unauthorized user!");
		}
	}
}
