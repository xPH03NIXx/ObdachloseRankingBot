package commands;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class BroadcastRankingCommand extends RankingCommand{
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        super.deferReply(event, false);
        MessageEmbed embed = super.generateRankedEmbed(event);
        super.replyEmbed(event, embed);
    }
}
