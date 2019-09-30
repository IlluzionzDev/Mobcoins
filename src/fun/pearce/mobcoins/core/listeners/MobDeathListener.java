package fun.pearce.mobcoins.core.listeners;

import fun.pearce.mobcoins.MobCoins;
import fun.pearce.mobcoins.core.data.MobcoinAPI;
import fun.pearce.mobcoins.struct.Lang;
import fun.pearce.mobcoins.utils.msg.MessageManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class MobDeathListener implements Listener {

    private MobCoins plugin = MobCoins.getInstance();
    private MessageManager msg = new MessageManager();

    /**
     * Handle when a mob dies and check if it drops mobcoins,
     * if so check chances and give the mob coins
     */
    @EventHandler
    public void onDeath(EntityDeathEvent e){

        if (e.getEntity().getKiller() == null){
            return;
        }

        String entityName = e.getEntityType().toString();
        Player player = e.getEntity().getKiller();
        MobcoinAPI api = new MobcoinAPI(player.getUniqueId());

        int chance = getMobChance(entityName);
        Random random = new Random();

        if(random.nextInt(100) < chance){
            msg.sendMsg(player, String.format(Lang.EVENT_COINSONKILL.getConfigValue(), "1", entityName));
            api.addCoins(1);
        }

    }

    /**
     * Get that chance of an entity dropping a mobcoin
     *
     * @param entity
     *      The entity to check
     * @return
     *      The chance of dropping it, defaults to 0 if entity not found
     */
    private int getMobChance(String entity){

        if (!plugin.getConfig().contains("Options.Mobs." + entity.toUpperCase())){
            return 0;
        } else {
            return plugin.getConfig().getInt("Options.Mobs." + entity.toUpperCase());
        }

    }

}
