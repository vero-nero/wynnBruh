import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CommandForceUpdate extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith(Main.bot.settings.getPrefix(event.getGuild().toString())) && msg.indexOf("fu") == Main.bot.settings.getPrefix(event.getGuild().toString()).length()) {
            String server = "WC";
            if (msg.trim().contains(" ")) {
                server += msg.trim().split(" ")[1];
            }

            ArrayList<String> onlinePlayers = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(Bot.readLog("wc.log"));
                for (int i = 0; i < ((JSONArray) json.get(server)).length(); i++) {
                    onlinePlayers.add(((JSONArray) json.get(server)).getString(i));
                }

                StringBuilder onlineLog = new StringBuilder(Bot.readLog("onlinePlayers.log"));
                FileWriter fw = new FileWriter("onlinePlayers.log", false);
                for (String player : onlinePlayers) {
                    onlineLog.insert(0, player + "\n");
                }
                fw.write(onlineLog.toString());
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bot.sendMessage(event.getChannel(), "Prioritising " + Bot.emojiGlobe + server + " with " + onlinePlayers.size() + " players");
        }
    }
}
