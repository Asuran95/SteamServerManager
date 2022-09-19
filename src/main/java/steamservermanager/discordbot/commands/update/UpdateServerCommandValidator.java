package steamservermanager.discordbot.commands.update;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import steamservermanager.discordbot.commands.DiscordCommandValidator;
import steamservermanager.eao.DiscordBotEAO;
import steamservermanager.models.DiscordBot;
import steamservermanager.utils.ServiceProvider;

public class UpdateServerCommandValidator extends DiscordCommandValidator {

	@Override
	public void validateEvent(MessageReceivedEvent event) {
		
		DiscordBotEAO discordBotEAO = ServiceProvider.provide(DiscordBotEAO.class);
		
		DiscordBot discordBot = discordBotEAO.find(1L);
		
		Long idLong = event.getAuthor().getIdLong();
		
		if (!idLong.equals(discordBot.getOwnerUserId())) {
			throw new RuntimeException("Unauthorized user!");
		}
		
	}
}
