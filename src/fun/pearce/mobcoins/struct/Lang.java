package fun.pearce.mobcoins.struct;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum Lang {

    /**
     * Messages sent when executing a command
     */
    COMMAND_NOPERMS("MSG.COMMAND.NOPERMS", "&cInsufficient Permissions"),
    COMMAND_BALANCE("MSG.COMMAND.BALANCE", "&b&lMOBCOINS &8× &7You have &b%s &7mobcoins"),
    COMMAND_BALANCEOTHERS("MSG.COMMAND.BALANCEOTHERS", "&b&lMOBCOINS &8× &b%s &7has &b%s &7mobcoins"),
    COMMAND_GIVECOINS("MSG.COMMAND.GIVE", "&b&lMOBCOINS &8× &7You gave &b%s %s &7mobcoins"),
    COMMAND_SETCOINS("MSG.COMMAND.SET", "&b&lMOBCOINS &8× &7You set &b%s's &7mobcoins to &b%s"),
    COMMAND_REMOVECOINS("MSG.COMMAND.REMOVE", "&b&lMOBCOINS &8× &7You removed &b%s &7mobcoins from &b%s"),
    COMMAND_WITHDRAWCOINS("MSG.COMMAND.WITHDRAW", "&b&lMOBCOINS &8× &7You withdrew &b%s &7mobcoins"),
    COMMAND_NOTENOUGHCOINS("MSG.COMMAND.NOTENOUGH", "&b&lMOBCOINS &8× &7You don't have that many mobcoins"),

    /**
     * Messages sent on events
     */
    EVENT_NOCOINS("MSG.EVENT.NOCOINS", "&b&lMOBCOINS &8× &7You don't have any mobcoins in your inventory"),
    EVENT_REDEEM("MSG.EVENT.REDEEM", "&b&lMOBCOINS &8× &7You have redeemed &b%s &7mobcoins"),
    EVENT_COINSONKILL("MSG.EVENT.COINSONKILL", "&b&lMOBCOINS &8× &7You have recieved &b%s &7mobcoins for killing a &b%s"),
    EVENT_PURCHASE("MSG.EVENT.PURCHASE", "&b&lMOBCOINS &8× &7You have purchased &b%s &7for &b%s &7mobcoins");

    /**
     * Path values used to set config values
     */
    private String path;
    private String def;
    private static FileConfiguration LANG;

    /**
     * Constructor to take values
     *
     * @param path
     *      The path the message will be stored at
     * @param start
     *      The default message to be set and used
     */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    /**
     * Set the Language file used
     *
     * @param config
     *      The file configuration to be used
     */
    public static void setFile(final FileConfiguration config) {
        LANG = config;
    }

    /**
     * Returns the default value
     *
     * @return
     *      The default config valued
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Returns the path to the value
     *
     * @return
     *      The path used in the config
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Returns the value in string form
     *
     * @return
     *      The colored message from the LANG file
     */
    public String getConfigValue() {
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, this.def));
    }

}
