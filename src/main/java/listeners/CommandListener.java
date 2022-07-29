package listeners;

import commands.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandListener extends ListenerAdapter {
    private static Map<String, Command> commands = new HashMap();
    {
        commands.put("help", new HelpCommand());
        commands.put("ranking", new RankingCommand());
        commands.put("addpoints", new AddPointsCommand());
        commands.put("removepoints", new RemovePointsCommand());
        commands.put("broadcastranking", new BroadcastRankingCommand());
        commands.put("resetranking", new ResetRankingCommand());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        commands.get(commandName).handle(event);
    }
}
