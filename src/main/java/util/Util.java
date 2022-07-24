package util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Map;

public class Util {
    public static String getUserIdentity(Member member) {
        return member.getUser().getAsTag();
    }

    public static MessageEmbed creteRankingEmbed(Map<String, Integer> map, int amount) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Ranking:");
        eb.setColor(Color.CYAN);

        if (amount==-1) {
            eb.setDescription("All Rankings");
        }else {
            eb.setDescription("Top "+amount+" users.");
        }

        String ranks = "";
        String names = "";
        String points = "";
        int i = 1;

        for (String name : map.keySet()) {
            if (amount==0) break;
            ranks = ranks.concat(String.valueOf(i)).concat("\n");
            names = names.concat(String.valueOf(name)).concat("\n");
            points = points.concat(String.valueOf(map.get(name))).concat("\n");
            i++;
            amount--;
        }

        eb.addField("Rank", ranks, true);
        eb.addField("Name", names, true);
        eb.addField("Points", points, true);
        return eb.build();
    }
}
