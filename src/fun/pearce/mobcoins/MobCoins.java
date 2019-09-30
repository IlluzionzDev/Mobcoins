package fun.pearce.mobcoins;

import fun.pearce.mobcoins.cmds.managers.CommandManager;
import fun.pearce.mobcoins.core.data.MobcoinAPI;
import fun.pearce.mobcoins.core.guis.GUIListener;
import fun.pearce.mobcoins.core.listeners.MobDeathListener;
import fun.pearce.mobcoins.core.listeners.RedeemListener;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.utils.config.ConfigWrapper;
import fun.pearce.mobcoins.utils.msg.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MobCoins extends JavaPlugin {

    /**
     * Instance of main class
     */
    private static MobCoins INSTANCE;

    /**
     * Hash of all the player balances
     */
    public static HashMap<UUID, Integer> balances = new HashMap<>();

    /**
     * Config files for use with data
     */
    private ConfigWrapper langFile = new ConfigWrapper(this, "", "lang.yml");

    /**
     * Gets the instance of the main class
     *
     * @return
     *      This class
     */
    public static MobCoins getInstance() {
        return INSTANCE;
    }

    /**
     * Ran on enabling of the plugin
     */
    @Override
    public void onEnable() {

        INSTANCE = this;

        registerListeners();
        saveDefaultConfig();
        loadLang();
        loadBal();
        new CommandManager();

    }

    /**
     * Ran on disabling of the plugin
     */
    @Override
    public void onDisable() {
        saveBal();
    }

    /**
     * Load the Lang File with all the messages used in the plugin
     */
    public void loadLang() {

        Lang.setFile(langFile.getConfig());

        for (final Lang value : Lang.values()) {
            langFile.getConfig().addDefault(value.getPath(), value.getDefault());
        }

        langFile.getConfig().options().copyDefaults(true);
        langFile.saveConfig();

    }

    /**
     * Register all listeners in the plugin
     */
    public void registerListeners() {

        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new GUIListener(), this);
        pm.registerEvents(new RedeemListener(), this);
        pm.registerEvents(new MobDeathListener(), this);

    }

    /**
     * Easily creates an item with custom lore and names, may add enchants aswell later
     *
     * @param name
     *      The name of the item
     * @param amount
     *      The amount to create
     * @param mat
     *      The material of the item
     * @return
     *      The created item
     */
    public static ItemStack createItem(String name, int amount, Material mat, short damage) {

        ItemStack stack = new ItemStack(mat, amount, damage);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(MessageManager.msg.color(name));

        stack.setItemMeta(meta);

        return stack;

    }

    /**
     * Easily creates an item with custom lore and names, may add enchants aswell later
     *
     * @param name
     *      The name of the item
     * @param amount
     *      The amount to create
     * @param mat
     *      The material of the item
     * @param lore
     *      The lore of the item
     * @return
     *      The created item
     */
    public static ItemStack createItem(String name, int amount, Material mat, String... lore) {

        ItemStack stack = new ItemStack(mat, amount);
        ItemMeta meta = stack.getItemMeta();

        ArrayList<String> list = new ArrayList<>();
        for(String s : lore){
            list.add(MessageManager.msg.color(s));
        }

        meta.setLore(list);
        meta.setDisplayName(MessageManager.msg.color(name));

        stack.setItemMeta(meta);

        return stack;

    }

    /**
     * Easily creates an item with custom lore and names, may add enchants aswell later
     *
     * @param name
     *      The name of the item
     * @param amount
     *      The amount to create
     * @param mat
     *      The material of the item
     * @param lore
     *      The lore of the item
     * @return
     *      The created item
     */
    public static ItemStack createItem(String name, int amount, Material mat, List<String> lore) {

        ItemStack stack = new ItemStack(mat, amount);
        ItemMeta meta = stack.getItemMeta();

        ArrayList<String> list = new ArrayList<>();
        for(String s : lore){
            list.add(MessageManager.msg.color(s));
        }

        meta.setLore(list);
        meta.setDisplayName(MessageManager.msg.color(name));

        stack.setItemMeta(meta);

        return stack;

    }

    /**
     * Easily creates an item with custom lore and names, may add enchants aswell later
     *
     * @param name
     *      The name of the item
     * @param amount
     *      The amount to create
     * @param mat
     *      The material of the item
     * @param lore
     *      The lore of the item
     * @return
     *      The created item
     */
    public static ItemStack createItem(int price, String name, int amount, Material mat, List<String> lore) {

        ItemStack stack = new ItemStack(mat, amount);
        ItemMeta meta = stack.getItemMeta();

        ArrayList<String> list = new ArrayList<>();
        for(String s : lore){
            list.add(MessageManager.msg.color(s.replaceAll("%price%", String.valueOf(price))));
        }

        meta.setLore(list);
        meta.setDisplayName(MessageManager.msg.color(name));

        stack.setItemMeta(meta);

        return stack;

    }

    /**
     * Easily creates an item with custom lore and names, may add enchants aswell later with formatting
     *
     * @param name
     *      The name of the item
     * @param amount
     *      The amount to create
     * @param mat
     *      The material of the item
     * @param lore
     *      The lore of the item
     * @return
     *      The created item
     */
    public static ItemStack createItem(UUID uuid, String name, int amount, Material mat, String... lore) {

        ItemStack stack = new ItemStack(mat, amount);
        ItemMeta meta = stack.getItemMeta();
        MobcoinAPI api = new MobcoinAPI(uuid);

        ArrayList<String> list = new ArrayList<>();
        for(String s : lore){
            list.add(MessageManager.msg.color(String.format(s, api.getCoins())));
        }

        meta.setLore(list);
        meta.setDisplayName(MessageManager.msg.color(name));

        stack.setItemMeta(meta);

        return stack;

    }

    /**
     * Easily creates an item with custom lore and names, may add enchants aswell later with formatting
     *
     * @param name
     *      The name of the item
     * @param amount
     *      The amount to create
     * @param mat
     *      The material of the item
     * @param lore
     *      The lore of the item
     * @return
     *      The created item
     */
    public static ItemStack createItem(UUID uuid, String name, int amount, Material mat, List<String> lore) {

        ItemStack stack = new ItemStack(mat, amount);
        ItemMeta meta = stack.getItemMeta();
        MobcoinAPI api = new MobcoinAPI(uuid);

        ArrayList<String> list = new ArrayList<>();
        for(String s : lore){
            list.add(MessageManager.msg.color(String.format(s, api.getCoins())));
        }

        meta.setLore(list);
        meta.setDisplayName(MessageManager.msg.color(name));

        stack.setItemMeta(meta);

        return stack;

    }

    /**
     * Save all balances to a config for restarts
     */
    public void saveBal() {
        File file = new File("data.yml");
        YamlConfiguration bal = YamlConfiguration.loadConfiguration(file);
        for (UUID uuid : this.balances.keySet()) {
            bal.set(uuid.toString(), this.balances.get(uuid));
        }
        try {
            bal.save(file);
        } catch (IOException ignored) {
        }
    }

    /**
     * Load all balances from the config the the map
     */
    private void loadBal() {
        File file = new File("data.yml");
        YamlConfiguration bal = YamlConfiguration.loadConfiguration(file);
        for (String uuid : bal.getKeys(false)) {
            this.balances.put(UUID.fromString(uuid), bal.getInt(uuid));
        }
    }

}
