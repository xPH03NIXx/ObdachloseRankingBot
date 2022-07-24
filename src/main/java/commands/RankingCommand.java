package commands;

import DB.DataBaseUtil;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import util.Util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RankingCommand implements Command{
    private boolean ephemeral = false;
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        deferReply(event, true);
        MessageEmbed embed = generateRankedEmbed(event);
        replyEmbed(event, embed);
    }

    protected void replyEmbed(SlashCommandInteractionEvent event, MessageEmbed embed) {
        event.getHook().sendMessageEmbeds(embed).setEphemeral(ephemeral).queue();
    }

    @NotNull
    protected MessageEmbed generateRankedEmbed(SlashCommandInteractionEvent event) {
        int amount = -1;
        OptionMapping option = event.getOption("amount");
        if (option!=null) {
            amount = event.getOption("amount").getAsInt();
        }

        Map<String, Integer> map = DataBaseUtil.getPoints();
        map = map.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    if (o1.getValue() > o2.getValue()) return -1;
                    if (o1.getValue().equals(o2.getValue())) return 0;
                    return 1;
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return Util.creteRankingEmbed(map,amount);
    }

    protected void deferReply(SlashCommandInteractionEvent event, boolean ephemeral) {
        this.ephemeral = ephemeral;
        event.deferReply(ephemeral).queue();
    }
}
