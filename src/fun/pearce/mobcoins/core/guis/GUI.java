package fun.pearce.mobcoins.core.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUI {

    /**
     * The variables independent to the GUI
     */
    private Inventory inventory;
    private Map<Integer, GuiAction> actions;
    private UUID uuid;

    /**
     * Maps to determine the GUI's
     */
    public static Map<UUID, GUI> inventoriesByUUID = new HashMap<>();
    public static Map<UUID, UUID> openInventories = new HashMap<>();

    /**
     * Create a new GUI
     *
     * @param invSize
     *      The size of the GUI, must be multiple of 9
     * @param invName
     *      The name of the GUI
     */
    public GUI(int invSize, String invName) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, invSize, invName);
        actions = new HashMap<>();
        inventoriesByUUID.put(getUuid(), this);
    }

    /**
     * Set an item in a gui with a onClick action
     *
     * @param slot
     *      The slot to set it in, from 0
     * @param stack
     *      The ItemStack to set there
     * @param action
     *      Lambda implementation of the action
     */
    public void setItem(int slot, ItemStack stack, GuiAction action){
        inventory.setItem(slot, stack);
        if (action != null){
            actions.put(slot, action);
        }
    }

    /**
     * Set an item in a gui without a onClick action
     *
     * @param slot
     *      The slot to set it in, from 0
     * @param stack
     *      The ItemStack to set there
     */
    public void setItem(int slot, ItemStack stack){
        setItem(slot, stack, null);
    }

    /**
     * Open a GUI to a player
     *
     * @param p
     *      The player to open the GUI to
     */
    public void open(Player p){
        p.openInventory(inventory);
        openInventories.put(p.getUniqueId(), getUuid());
    }

    /**
     * Remove the GUI from exsistence and close all instances of it
     */
    public void delete(){
        for (Player p : Bukkit.getOnlinePlayers()){
            UUID u = openInventories.get(p.getUniqueId());
            if (u.equals(getUuid())){
                p.closeInventory();
            }
        }
        inventoriesByUUID.remove(getUuid());
    }

    /**
     * Return the UUID of the GUi
     *
     * @return
     *      Return the UUID of the GUi
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Return the Inventory object of the GUI
     *
     * @return
     *      The inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Return a Map of all created inventories
     *
     * @return
     *      A Map of the GUI's
     */
    public static Map<UUID, GUI> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    /**
     * Return a Map of all open inventories
     *
     * @return
     *      A Map of the GUI's
     */
    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    /**
     * Return a Map of all GUI actions
     *
     * @return
     *      A Map of the GUI actions
     */
    public Map<Integer, GuiAction> getActions() {
        return actions;
    }

    /**
     * The interface to implement for an action
     */
    public interface GuiAction {
        void click(Player player);
    }

}
