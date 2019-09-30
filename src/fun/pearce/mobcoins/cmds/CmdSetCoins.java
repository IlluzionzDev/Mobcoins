package fun.pearce.mobcoins.cmds;

import fun.pearce.mobcoins.cmds.managers.SubCommand;
import fun.pearce.mobcoins.core.data.MobcoinAPI;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.struct.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSetCoins extends SubCommand {

    public CmdSetCoins(){
        name = "set";
        permission = Permission.CMD_SET_BALANCE;
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

        // Make sure they don't add negatives
        if (amount < 0){
            msg.sendMsg(player, "&cAmount cannot be negative");
            return;
        }

        msg.sendMsg(player, String.format(Lang.COMMAND_SETCOINS.getConfigValue(), p.getName(), amount));

        api.setCoins(amount);

    }

}
