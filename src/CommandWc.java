import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CommandWc extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().startsWith(Main.bot.settings.getPrefix(event.getGuild().toString())) && msg.getContentRaw().indexOf("wc") == Main.bot.settings.getPrefix(event.getGuild().toString()).length()) {
            MessageChannel channel = event.getChannel();

            JSONObject json = null;
            try {
                json = Bot.readJsonFromUrl("https://api.wynncraft.com/public_api.php?action=onlinePlayers");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Bot.writeFile("commandWC.log", e.getLocalizedMessage() + "\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            assert json != null;

            String server = null;
            if (msg.getContentRaw().trim().contains(" ")) {
                server = msg.getContentRaw().trim().split(" ")[1];
            }

            StringBuilder message = new StringBuilder();
            Iterator<String> keys = json.keys();

            if (Bot.isNumeric(server)) {
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (key.equals("request"))
                        continue;

                    if (json.get(key) instanceof JSONArray && key.equals("WC" + server.trim())) {
                        message.append(Bot.emojiGlobe).append(" ").append(key).append(": ").append(((JSONArray) json.get(key)).length()).append(" online\r\n");
                        for (int i = 0; i < ((JSONArray) json.get(key)).length(); i++) {
                            message.append(((JSONArray) json.get(key)).get(i));
                            message.append("\n");
                        }
                    }
                }
            } else {
                HashMap<String, Integer> wcMap = new HashMap<>();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (key.equals("request"))
                        continue;

                    if (json.get(key) instanceof JSONArray) {
                        if (((JSONArray) json.get(key)).length() != 0) {
                            wcMap.put(key, ((JSONArray) json.get(key)).length());
                        }
                    }
                }
                wcMap = Bot.sortByIntValue(wcMap);
                for (Map.Entry<String, Integer> en : wcMap.entrySet()) {
                    message.append(Bot.emojiGlobe).append(" ").append(en.getKey()).append(": ").append(en.getValue()).append(" online\r\n");
                }
            }

            Bot.sendMessage(channel, message.toString());
        }
    }
}
