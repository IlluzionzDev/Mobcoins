package fun.pearce.mobcoins.core.guis.menus;

import fun.pearce.mobcoins.MobCoins;
import fun.pearce.mobcoins.core.data.MobcoinAPI;
import fun.pearce.mobcoins.core.guis.GUI;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.utils.msg.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MobcoinMenu extends GUI {

    /**
     * Instance of variables for menu
     */
    private MobCoins plugin = MobCoins.getInstance();
//    private int invSize = plugin.getConfig().getInt("Options.Inventory.size");
//    private String invName = plugin.getConfig().getString("Options.Inventory.name");

    /**
     * Variables for balance item
     */
    private int balanceSlot = plugin.getConfig().getInt("Options.Balance.slot");
    private Material balanceMaterial = Material.valueOf(plugin.getConfig().getString("Options.Balance.material"));
    private String balanceName = plugin.getConfig().getString("Options.Balance.name");
    private List<String> balanceLore = plugin.getConfig().getStringList("Options.Balance.lore");

    /**
     * Variables for information item
     */
    private int infoSlot = plugin.getConfig().getInt("Options.Info.slot");
    private Material infoMaterial = Material.valueOf(plugin.getConfig().getString("Options.Info.material"));
    private String infoName = plugin.getConfig().getString("Options.Info.name");
    private List<String> infoLore = plugin.getConfig().getStringList("Options.Info.lore");

    /**
     * Variables for border item
     */
    private Material borderMaterial = Material.valueOf(plugin.getConfig().getString("Options.Border.material"));
    private String borderName = plugin.getConfig().getString("Options.Border.name");
    private short borderDamage = (short) plugin.getConfig().getInt("Options.Border.damage");

    /**
     * Create a new GUI
     */
    public MobcoinMenu(UUID uuid) {
        super(54, "Mob Coins Shop");

        // Set balance item
        setItem(balanceSlot, MobCoins.createItem(uuid, balanceName, 1, balanceMaterial, balanceLore));

        // Set information item
        setItem(infoSlot, MobCoins.createItem(infoName, 1, infoMaterial, infoLore));

        // Setting the borders
        ItemStack borderItem = MobCoins.createItem(borderName, 1, borderMaterial, borderDamage);
        List<Integer> borderSlots = plugin.getConfig().getIntegerList("Options.Inventory.borderSlots");
        borderSlots.forEach(borderSlot -> {
            setItem(borderSlot, borderItem);
        });

        // Set each of the purchasable items
        for (String key : plugin.getConfig().getConfigurationSection("Options.Shop").getKeys(false)) {

            // Misc Options
            int slot = plugin.getConfig().getInt("Options.Shop." + key + ".slot");
            int price = plugin.getConfig().getInt("Options.Shop." + key + ".price");
            String friendlyName = MessageManager.msg.color(plugin.getConfig().getString("Options.Shop." + key + ".friendlyName"));
            List<String> commands = plugin.getConfig().getStringList("Options.Shop." + key + ".commands");

            // Item Options
            Material material = Material.valueOf(plugin.getConfig().getString("Options.Shop." + key + ".Item.material"));
            String name = MessageManager.msg.color(plugin.getConfig().getString("Options.Shop." + key + ".Item.name"));
            int amount = plugin.getConfig().getInt("Options.Shop." + key + ".Item.amount");
            List<String> lore = plugin.getConfig().getStringList("Options.Shop." + key + ".Item.lore");

            // Setting the item and handling the purchasing
            setItem(slot, MobCoins.createItem(price, name, amount, material, lore), player -> {

                MobcoinAPI api = new MobcoinAPI(player.getUniqueId());

                if (api.getCoins() < price) {

                    MessageManager.msg.sendMsg(player, Lang.COMMAND_NOTENOUGHCOINS);
                    return;

                } else {

                    for (String command : commands) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replaceAll("%player%", player.getName()));
                    }

                    api.removeCoins(price);
                    MessageManager.msg.sendMsg(player, String.format(Lang.EVENT_PURCHASE.getConfigValue(), friendlyName, price));

                }

            });

        }

    }

}
