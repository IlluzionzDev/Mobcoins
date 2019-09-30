package fun.pearce.mobcoins.cmds;

import fun.pearce.mobcoins.cmds.managers.SubCommand;
import fun.pearce.mobcoins.core.data.MobcoinAPI;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.struct.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdBalance extends SubCommand {

    public CmdBalance(){
        name = "balance";
        permission = Permission.CMD_CHECK_BALANCE;
    }

    @Override
    public void onCommand(CommandSender player, String[] args) {
        if(!(player instanceof Player)){
            return;
        }

        Player p = (Player) player;

        MobcoinAPI coins = new MobcoinAPI(p.getUniqueId());

        // Checking if they don't specify a player
        if (args.length == 0){
            msg.sendMsg(player, String.format(Lang.COMMAND_BALANCE.getConfigValue(), coins.getCoins()));
        } else {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null){
                msg.sendMsg(player, "&cPlayer not found");
                return;
            }

            MobcoinAPI api = new MobcoinAPI(target.getUniqueId());

            msg.sendMsg(player, String.format(Lang.COMMAND_BALANCEOTHERS.getConfigValue(), target.getName(), api.getCoins()));
        }


    }

}
