import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        // This is the event that is triggered when a member joins the guild

        // You can access the member who joined using event.getMember()
        // You can access the guild that the member joined using event.getGuild()

        // You can do whatever you want in this event handler, such as sending a welcome message to the member
        event.getChannel().sendMessage("Welcome to the server, " + event.getMember().getAsMention() + "!").queue();
    }
}