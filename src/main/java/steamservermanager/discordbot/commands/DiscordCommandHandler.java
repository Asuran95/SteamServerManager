package steamservermanager.discordbot.commands;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class DiscordCommandHandler implements DiscordCommand {
	
	private DiscordCommandValidator validator;
	
	public DiscordCommandHandler() {}
	
	public DiscordCommandHandler(DiscordCommandValidator validator) {
		this.validator = validator;
	}

	@Override
	public void performAction(SlashCommandInteractionEvent event) {
		
		try {
			if (validator != null) {
				validator.validateEvent(event);
			}

			action(event);
			
		} catch (Exception ex) {
			MessageChannelUnion channel = event.getChannel();
			
			channel.sendMessage("`" + ex.getMessage() + "`").complete();
		}
	}
	
	protected abstract void action(SlashCommandInteractionEvent event);

}
