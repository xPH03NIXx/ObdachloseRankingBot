package commands;

import DB.DataBaseUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class RemovePointsCommand implements Command{
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        String name = event.getOption("username").getAsUser().getAsTag();
        int points = event.getOption("points").getAsInt();

        points *= -1;

        DataBaseUtil.updatePoints(name, points);
        event.getHook().sendMessage("Success").queue();
    }
}
