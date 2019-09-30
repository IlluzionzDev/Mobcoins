package fun.pearce.mobcoins.core.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class GUIListener implements Listener {

    /**
     * Handle the actions when the item is clicked on
     *
     * @param e
     *      The click event being passed
     */
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (!(e.getWhoClicked() instanceof Player)){
            return;
        }
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        UUID inventoryUUID = GUI.openInventories.get(playerUUID);
        if (inventoryUUID != null){
            e.setCancelled(true);
            GUI gui = GUI.getInventoriesByUUID().get(inventoryUUID);
            GUI.GuiAction action = gui.getActions().get(e.getSlot());

            if (action != null){
                action.click(player);
            }
        }
    }

    /**
     * Handle when a player closes a GUI
     *
     * @param e
     *      The close event being passed
     */
    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        GUI.openInventories.remove(playerUUID);
    }

    /**
     * Handles when a player leaves the game
     *
     * @param e
     *      The quit event being passed
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        GUI.openInventories.remove(playerUUID);
    }

}
