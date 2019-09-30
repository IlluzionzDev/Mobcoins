package fun.pearce.mobcoins.cmds;

import fun.pearce.mobcoins.cmds.managers.SubCommand;
import fun.pearce.mobcoins.core.data.MobcoinAPI;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.struct.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRemoveCoins extends SubCommand {

    public CmdRemoveCoins(){
        name = "remove";
        permission = Permission.CMD_REMOVE_BALANCE;
    }

    @Override
    public void onCommand(CommandSender player, String[] args) {

        Player p = Bukkit.getPlayer(args[0]);
        int amount = Integer.valueOf(args[1]);

        // Check if the player is not online
        if (p == null) {
            msg.sendMsg(player, "&cPlayer not found");
            return;
        }

        MobcoinAPI api = new MobcoinAPI(p.getUniqueId());

        // Make sure they don't take away negatives
        if (amount < 0){
            msg.sendMsg(player, "&cAmount cannot be negative");
            return;
        }

        msg.sendMsg(player, String.format(Lang.COMMAND_REMOVECOINS.getConfigValue(), amount, p.getName()));

        api.removeCoins(amount);

    }

}
