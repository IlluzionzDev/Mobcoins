package fun.pearce.mobcoins.cmds.managers;

import fun.pearce.mobcoins.MobCoins;
import fun.pearce.mobcoins.cmds.*;
import fun.pearce.mobcoins.core.guis.menus.MobcoinMenu;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.utils.msg.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager implements CommandExecutor {

    /**
     * A holder for all our subcommands
     */
    private ArrayList<SubCommand> commands = new ArrayList<>();

    /**
     * Instance of the main class
     */
    private MobCoins plugin = MobCoins.getInstance();

    /**
     * Instance of the command manager to send messages
     */
    private MessageManager msg = new MessageManager();

    /**
     * The main command to be used in execution
     */
    private String main = "mobcoins";

    public CommandManager() {
        setup();
    }

    /**
     * Add all the commands
     */
    public void setup() {
        plugin.getCommand(main).setExecutor(this);
        commands.add(new CmdBalance());
        commands.add(new CmdGiveCoins());
        commands.add(new CmdSetCoins());
        commands.add(new CmdRemoveCoins());
        commands.add(new CmdWithdraw());
    }

    /**
     * Runs when the main command is executed and checks if the first arg
     * is a subcommand and if the have the required permission,
     * if so it executes it's implementation.
     */
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase(main) || cmd.getName().equalsIgnoreCase("mc")
                    || cmd.getName().equalsIgnoreCase("mobcoin")) {
                if (args.length == 0) {
                    MobcoinMenu menu = new MobcoinMenu(player.getUniqueId());
                    menu.open(player);
                    return true;
                }

                SubCommand target = this.get(args[0]);
                if (target == null) {
                    showHelp(player);
                    return true;
                }

                if (!target.hasPerms(player)) {
                    msg.sendMsg(player, Lang.COMMAND_NOPERMS);
                    return false;
                }

                ArrayList<String> a = new ArrayList<String>();
                a.addAll(Arrays.asList(args));
                a.remove(0);
                args = a.toArray(new String[a.size()]);

                try {
                    target.onCommand(player, args);
                } catch (Exception var9) {
                    msg.sendMsg(player, "&cAn error has occured: " + var9.getCause());
                    var9.printStackTrace();
                }
            }

        }
        return true;

    }

    /**
     * Get a SubCommand from a string
     *
     * @param name
     *      What to fetch the subcommand from
     * @return
     *      The arg as a subcommand
     */
    private SubCommand get(String name) {

        for (SubCommand cmd : this.commands) {
            if (cmd.getName().equalsIgnoreCase(name)) {
                return cmd;
            }
        }

        return null;
    }

    /**
     * Show a help message to the player
     */
    private void showHelp(Player player){
        msg.sendMsg(player, " ");
        msg.sendMsg(player, "&b&lMOBCOINS HELP");
        msg.sendMsg(player, " ");
        msg.sendMsg(player, "&b/mobcoins - &7Opens the GUI");
        msg.sendMsg(player, "&b/mobcoins balance - &7Check your mobcoin balance");
        msg.sendMsg(player, "&b/mobcoins give <player> <amount> - &7Give a player mobcoins");
        msg.sendMsg(player, "&b/mobcoins set <player> <amount> - &7Set a player mobcoins");
        msg.sendMsg(player, "&b/mobcoins remove <player> <amount> - &7Remove mobcoins from a player");
        msg.sendMsg(player, "&b/mobcoins withdraw <amount> - &7Withdraw coins into physical items");
        msg.sendMsg(player, " ");
    }

}
