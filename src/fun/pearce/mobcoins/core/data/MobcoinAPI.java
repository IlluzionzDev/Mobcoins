package fun.pearce.mobcoins.core.data;

import fun.pearce.mobcoins.MobCoins;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.utils.msg.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class MobcoinAPI {

    private MobCoins plugin = MobCoins.getInstance();

    /**
     * Variables for mobcoins item
     */
    private String coinName = plugin.getConfig().getString("Options.ItemToken.name");
    private Material coinMaterial = Material.valueOf(plugin.getConfig().getString("Options.ItemToken.material"));

    /**
     * The UUID for the player to handle data for
     */
    private UUID uuid;

    /**
     * Create instance of the API to handle token data
     *
     * @param uuid
     *      The player's UUID to handle tokens for
     */
    public MobcoinAPI(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * This will loop through the players inventory
     * and check for all token items, then it
     * will add that many tokens to the players balance
     */
    public void redeemTokens() {

        Player player = Bukkit.getPlayer(uuid);

        int amount = 0;

        for (ItemStack item : player.getInventory().getContents()){
            if (item == null || !item.hasItemMeta()){
                continue;
            }
            if (item.getItemMeta().getDisplayName().equals(MessageManager.msg.color(coinName)) && item.getType() == coinMaterial){
                amount += item.getAmount();
            }
        }

        if (amount <= 0){
            MessageManager.msg.sendMsg(player, Lang.EVENT_NOCOINS);
            return;
        }

        player.getInventory().remove(coinMaterial);

        MessageManager.msg.sendMsg(player, String.format(Lang.EVENT_REDEEM.getConfigValue(), amount));
        addCoins(amount);

    }

    /**
     * Set the players token balance to amount.
     * current = amount
     *
     * @param amount
     *      The amount to set tokens to
     */
    public void setCoins(int amount) {
        MobCoins.balances.put(uuid, amount);
    }

    /**
     * Add tokens to the players token balance.
     * current = current + amount
     *
     * @param amount
     *      The amount of tokens to add
     */
    public void addCoins(int amount) {

        if(!MobCoins.balances.containsKey(uuid)){
            MobCoins.balances.put(uuid, amount);
        } else {
            MobCoins.balances.put(uuid, MobCoins.balances.get(uuid) + amount);
        }

    }

    /**
     * Remove tokens from the players token balance.
     * current = current - amount
     *
     * @param amount
     *      The amount of tokens to remove
     */
    public void removeCoins(int amount) {
        if(!MobCoins.balances.containsKey(uuid)){
            MobCoins.balances.put(uuid, amount);
        } else {
            MobCoins.balances.put(uuid, MobCoins.balances.get(uuid) - amount);
        }
    }


    /**
     * Get the amount of tokens a player has
     *
     * @return
     *      The amount of tokens in their token balance
     */
    public int getCoins() {
        if(!MobCoins.balances.containsKey(uuid)){
            setCoins(0);
            return 0;
        } else {
            return MobCoins.balances.get(uuid);
        }
    }

}
