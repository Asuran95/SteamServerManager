package steamservermanager.discordbot.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import steamservermanager.eao.ServerGameEAO;
import steamservermanager.models.ServerGame;
import steamservermanager.utils.ServiceProvider;

public class DiscordBotSlashCommandHelper {

	public static void updateCommands(JDA jda) {
		CommandListUpdateAction commands = jda.updateCommands();

		OptionData subcommandUpdateGameOption = populateGameOption(
				new OptionData(OptionType.INTEGER, "name", "Game's name", true));

		OptionData subcommandServerNameOption = populateServerOption(
				new OptionData(OptionType.INTEGER, "name", "Server's name", true));

		commands.addCommands(Commands.slash("show", "Show information about the server list")
				.addSubcommands(new SubcommandData("list", "Shows all the servers.")));

		commands.addCommands(Commands.slash("server", "Starts or stops one of the available servers from the list.")
				.addSubcommands(new SubcommandData("start", "Starts one of the available servers from the list.")
						.addOptions(subcommandServerNameOption))
				.addSubcommands(new SubcommandData("stop", "Stops one of the available servers from the list.")
						.addOptions(subcommandServerNameOption)));

		commands.addCommands(
				Commands.slash("update", "Update all servers, all servers of a specific game or just a specific server")
						.addSubcommands(new SubcommandData("all", "Updates every server from the list."))
						.addSubcommands(new SubcommandData("game", "Updates all games from it's Steam ID")
								.addOptions(subcommandUpdateGameOption))
						.addSubcommands(new SubcommandData("server", "Updates a specific server")
								.addOptions(subcommandServerNameOption)));

		commands.queue();
	}

	private static OptionData populateGameOption(OptionData option) {
		List<ServerGame> serverList = ServiceProvider.provide(ServerGameEAO.class).findAll();

		Map<Integer, String> gameNameMap = new HashMap<>();

		serverList.stream().forEach(entry -> gameNameMap.put(entry.getAppID(), entry.getGameName()));

		gameNameMap.forEach((appId, gameName) -> {
			option.addChoice(gameName, appId);
		});

		return option;
	}

	private static OptionData populateServerOption(OptionData option) {
		List<ServerGame> serverList = ServiceProvider.provide(ServerGameEAO.class).findAll();

		for (ServerGame serverGame : serverList) {
			option.addChoice(serverGame.getName(), serverGame.getIdServerGame());
		}

		return option;
	}
}
