import java.io.*;
import java.util.Properties;

public class Settings {
    private final Properties properties = new Properties();
    private String prefix;

    public Settings() throws IOException {
        File propFile = new File("settings.properties");
        if (propFile.exists()) {
            properties.load(new FileInputStream("settings.properties"));
            prefix = properties.getProperty("prefix") == null ? "+" : properties.getProperty("prefix");
        } else {
            if (propFile.createNewFile()) {
                System.out.println("settings.properties created");
            }
            properties.load(new FileInputStream("settings.properties"));
            if (properties.getProperty("prefix") != null) {
                prefix = properties.getProperty("prefix");
            }
            setPrefix("", prefix);
        }
        System.out.println("Prefix is '" + prefix + "'");
    }

    public String getPrefix(String guild) {
        if (properties.getProperty("prefix"+guild) != null)
            return properties.getProperty("prefix"+guild);
        else {
            setPrefix(guild, "+");
            return "+";
        }
    }

    public void setPrefix(String guild, String prefix) {
        this.prefix = prefix;
        properties.setProperty("prefix" + guild, prefix);
        saveProperties();
    }

    private void saveProperties() {
        try {
            OutputStream os = new FileOutputStream("settings.properties");
            properties.store(os, "settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
