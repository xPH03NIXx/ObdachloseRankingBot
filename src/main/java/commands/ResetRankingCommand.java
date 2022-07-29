package commands;

import DB.DataBaseUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ResetRankingCommand implements Command{
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        DataBaseUtil.resetList(event.getGuild().getMembers());
    }
}
