package fun.pearce.mobcoins.utils.msg;

import fun.pearce.mobcoins.struct.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

    public static MessageManager msg;

    /**
     * Assigns the instance to this class when an instance is created
     */
    public MessageManager() {
        msg = this;
    }

    /**
     * Use for sending a message from Msg
     *
     * @param sender
     *      Whoever is receiving the message
     * @param msg
     *      The enum to be used as the message
     */
    public void sendMsg(CommandSender sender, Lang msg) {
        String str = msg.getConfigValue();
        sender.sendMessage(str);
    }

    /**
     * Send coloured strings
     *
     * @param sender
     *      Whoever is receiving the message
     * @param msg
     *      The message (accepts colour codes)
     */
    public void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    /**
     * Sends a good message to the player
     *
     * @param player
     *      Whoever is receiving the message
     * @param msg
     *      The msg you want to send
     */
    public void playerGood(CommandSender player, String msg) {
        player.sendMessage(ChatColor.GREEN + msg);
    }

    /**
     * Sends a bad message to the player
     *
     * @param player
     *      Whoever is receiving the message
     * @param msg
     *      The msg you want to send
     */
    public void playerBad(CommandSender player, String msg) {
        player.sendMessage(ChatColor.RED + msg);
    }

    /**
     * Use to colour strings
     *
     * @param str
     *      The string you want to colour
     * @return
     *      The coloured string
     */
    public String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
