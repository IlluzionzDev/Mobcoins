package fun.pearce.mobcoins.cmds.managers;

import fun.pearce.mobcoins.struct.Permission;
import fun.pearce.mobcoins.utils.msg.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    /**
     * The name of the subcommand to be passed as an argument
     */
    public String name;

    /**
     * The permission required to execute this command
     */
    public Permission permission;

    /**
     * The message manager instance
     */
    public MessageManager msg = new MessageManager();

    /**
     * Implemented then called when name matches first arg
     *
     * @param player
     *      The sender doing the command
     * @param args
     *      The args passed to the command
     */
    public abstract void onCommand(CommandSender player, String[] args);

    /**
     * Return the name of the subcommand
     *
     * @return
     *      The name of the SubCommand
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if they have the permission for the subcommand
     *
     * @param player
     *      The player to check perm for
     * @return
     *      Whether they have the permissions
     */
    public boolean hasPerms(Player player){
        return player.hasPermission(permission.getPermissionNode());
    }

}
