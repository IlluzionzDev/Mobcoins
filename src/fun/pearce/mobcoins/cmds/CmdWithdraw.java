package fun.pearce.mobcoins.cmds;

import fun.pearce.mobcoins.MobCoins;
import fun.pearce.mobcoins.cmds.managers.SubCommand;
import fun.pearce.mobcoins.core.data.MobcoinAPI;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.struct.Permission;
import fun.pearce.mobcoins.utils.sound.SoundManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdWithdraw extends SubCommand {

    private MobCoins plugin = MobCoins.getInstance();

    /**
     * Variables for mobcoins item
     */
    private Material coinMaterial = Material.valueOf(plugin.getConfig().getString("Options.ItemToken.material"));
    private String coinName = plugin.getConfig().getString("Options.ItemToken.name");
    private List<String> coinLore = plugin.getConfig().getStringList("Options.ItemToken.lore");

    public CmdWithdraw(){
        name = "withdraw";
        permission = Permission.CMD_WITHDRAW_BALANCE;
    }

    @Override
    public void onCommand(CommandSender player, String[] args) {

        Player p = (Player) player;
        int amount = Integer.valueOf(args[0]);

        MobcoinAPI api = new MobcoinAPI(p.getUniqueId());

        // Make sure they don't add negatives
        if (amount < 0){
            msg.sendMsg(player, "&cAmount cannot be negative");
            return;
        }

        if (api.getCoins() < amount){
           msg.sendMsg(player, Lang.COMMAND_NOTENOUGHCOINS);
           return;
        }

        msg.sendMsg(player, String.format(Lang.COMMAND_WITHDRAWCOINS.getConfigValue(), amount));

        api.removeCoins(amount);
        p.getInventory().addItem(MobCoins.createItem(coinName, amount, coinMaterial, coinLore));
        Sound sound = Sound.valueOf(plugin.getConfig().getString("Options.Sound.withdrawCoins.name"));
        float volume = (float) plugin.getConfig().getDouble("Options.Sound.withdrawCoins.volume");
        float pitch = (float) plugin.getConfig().getDouble("Options.Sound.withdrawCoins.pitch");

        p.playSound(p.getLocation(), sound, volume, pitch);

    }
}
