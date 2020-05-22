package fun.pearce.mobcoins.core.listeners;

import fun.pearce.mobcoins.MobCoins;
import fun.pearce.mobcoins.core.data.MobcoinAPI;
import fun.pearce.mobcoins.utils.msg.MessageManager;
import fun.pearce.mobcoins.utils.sound.SoundManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class RedeemListener implements Listener {

    private MobCoins plugin = MobCoins.getInstance();

    /**
     * Variables for mobcoins item
     */
    private String coinName = plugin.getConfig().getString("Options.ItemToken.name");

    /**
     * Controls redeeming of the item to add to the balance
     */
    @EventHandler
    public void redeem(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        ItemStack heldItem = player.getInventory().getItemInHand();

        if (heldItem == null) {
            return;
        }

        if (!heldItem.hasItemMeta()){
            return;
        }

        if (heldItem.getType() == null || heldItem.getType() == Material.AIR) {
            return;
        }

        if (!heldItem.getItemMeta().getDisplayName().equals(MessageManager.msg.color(coinName))) {
            return;
        }

        MobcoinAPI api = new MobcoinAPI(uuid);
        e.setCancelled(true);
        api.redeemTokens();
        Sound sound = Sound.valueOf(plugin.getConfig().getString("Options.Sound.addCoins.name"));
        float volume = (float) plugin.getConfig().getDouble("Options.Sound.addCoins.volume");
        float pitch = (float) plugin.getConfig().getDouble("Options.Sound.addCoins.pitch");

        player.playSound(player.getLocation(), sound, volume, pitch);

    }

}
