package fun.pearce.mobcoins.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigWrapper {

    /**
     * Values to be set
     */
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;
    private final String folderName, fileName;

    /**
     * Contructor for making new file
     *
     * @param instance
     *      The instance of a JavaPlugin
     * @param folderName
     *      The directory to the folder
     * @param fileName
     *      The actual name of the file
     */
    public ConfigWrapper(final JavaPlugin instance, final String folderName, final String fileName) {
        this.plugin = instance;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    /**
     * Create a new file
     *
     * @param message
     *      Optional message to send
     * @param header
     *      A header
     */
    public void createNewFile(final String message, final String header) {
        reloadConfig();
        saveConfig();
        loadConfig(header);

        if (message != null) {
            plugin.getLogger().info(message);
        }
    }

    /**
     * Returns the FileConfiguration object from the file
     *
     * @return
     *      The FileConfiguration object
     */
    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    /**
     * Load the config file
     *
     * @param header
     *      The header
     */
    public void loadConfig(final String header) {
        config.options().header(header);
        config.options().copyDefaults(true);
        saveConfig();
    }

    /**
     * Reload the config
     */
    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder() + folderName, fileName);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Save the config
     */
    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            getConfig().save(configFile);
        } catch (final IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

}
