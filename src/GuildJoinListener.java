import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
  TextChannel channel = event.getGuild().getDefaultChannel().asTextChannel();
  channel.sendMessage(SlashCommands.AMOGUS + "@" +event.getMember().getAsMention()).queue();
    }
}