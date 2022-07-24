package commands;

import DB.DataBaseUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AddPointsCommand implements Command{
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        String name = event.getOption("username").getAsUser().getAsTag();
        int points = event.getOption("points").getAsInt();

        DataBaseUtil.updatePoints(name, points);
        event.getHook().sendMessage("Success").queue();
    }
}
