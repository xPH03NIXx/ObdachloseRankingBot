import DB.DataBaseUtil;
import listeners.CommandListener;
import listeners.JoinLeaveListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) {
        //TODO: Extract to class
        // Handle exception properly
        try {
            JDA jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                    //.setActivity(Activity.listening(System.getenv("ACTIVITY_NAME")))
                    .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                    .setMemberCachePolicy(MemberCachePolicy.ALL) // ignored if chunking enabled
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(new CommandListener(), new JoinLeaveListener())
                    .build().awaitReady();

            Guild guild = jda.getGuildById(System.getenv("TEST_GUILD"));
            assert guild != null;

            guild.upsertCommand("help", "a help command").queue();
            guild.upsertCommand("ranking", "displays all users with their points - descending").queue();
            guild.upsertCommand(new CommandDataImpl("addpoints", "increases Points for a given user")
                    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                    .addOptions(new OptionData(OptionType.USER, "username", "The username of the user. For Example ``MaxMusterman#1234``.", true),
                            new OptionData(OptionType.INTEGER, "points", "The points to add to the users record.", true)
                                    .setMinValue(1)
                                    .setMaxValue(100))).queue();
            guild.upsertCommand(new CommandDataImpl("removepoints", "decreases Points for a given user")
                    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                    .addOptions(new OptionData(OptionType.USER, "username", "The username of the user. For Example ``MaxMusterman#1234``.", true),
                            new OptionData(OptionType.INTEGER, "points", "The points to add to the users record.", true)
                                    .setMinValue(1)
                                    .setMaxValue(100))).queue();
            guild.upsertCommand(new CommandDataImpl("broadcastranking", "broadcast the ranking to the channel, visible to everyone.")
                    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                    .addOptions(new OptionData(OptionType.INTEGER, "amount", "amount of users to display - if left empty, all users will be displayed")
                            .setMinValue(1))).queue();


            DataBaseUtil.initialize(guild.getMembers());
            System.out.println(DataBaseUtil.getPoints());
        } catch (LoginException e) {
            System.out.println("failed to log into Discord");
        } catch (InterruptedException e) {
            System.out.println("connection interrupted");
        }

    }
}
