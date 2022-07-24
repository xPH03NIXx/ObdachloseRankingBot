package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class HelpCommand implements Command {

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();
        event.getHook().sendMessageEmbeds(creteHelpEmbed()).setEphemeral(true).queue();
    }

    private MessageEmbed creteHelpEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Hilfe zum Bot:**");
        eb.setColor(Color.CYAN);
        eb.setDescription("Dieser Hilfetext beschreibt alle Funktionen und wie sie zu benutzen sind.");
        eb.addField("Slash Commands:", "Verwendung in jedem beliebigen Textchat.", false);
        eb.addField("/help", "Befehl, der diese Hilfe-Nachricht sendet. Nur du kannst diese Nachricht sehen.", true);
        eb.addField("/ranking +amount", "Zeigt die Rankingtabelle aller User auf diesem Discord-Server an. Der optionale Parameter \"amount\" kann die Anzahl der angezeigten Einträge begrenzen.\n**Nur du** kannst das Ergebnis sehen.", true);
        eb.addField("Slash Commands für Admins:", "Verwendung in jedem beliebigen Textchat.", false);
        eb.addField("/addpoints user points", "Befehl, zum Hinzufügen von Punkten vom Score eines bestimmten User.\nDer Wertebereich von amount ist *1-100*. **Nur du** kannst diese Nachricht sehen.", true);
        eb.addField("/removepoints user points", "Befehl, zum Entfernen von Punkten vom Score eines bestimmten User.\nDer Wertebereich von amount ist *1-100*. **Nur du** kannst diese Nachricht sehen.", true);
        eb.addField("/broadcastranking +amount", "Zeigt die Rankingtabelle aller User auf diesem Discord-Server an. Der optionale Parameter \"amount\" kann die Anzahl der angezeigten Einträge begrenzen.\n**Jeder** kannst das Ergebnis sehen.", true);
        return eb.build();
    }
}
