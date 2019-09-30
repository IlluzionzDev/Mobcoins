package fun.pearce.mobcoins.utils.sound;

import fun.pearce.mobcoins.MobCoins;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {

    public static SoundManager snd;
    private MobCoins plugin = MobCoins.getInstance();

    /**
     * Assigns the instance to this class when an instance is created
     */
    public SoundManager() {
        snd = this;
    }

    /**
     * Use for sending a sound from config
     *
     * @param player    Whoever is receiving the sound
     * @param soundName The String to extract data from
     */
    public void sendSound(Player player, String soundName) {

        Sound sound = Sound.valueOf(plugin.getConfig().getString("Options.Sound." + soundName + ".name"));
        float volume = (float) plugin.getConfig().getDouble("Options.Sound." + soundName + ".volume");
        float pitch = (float) plugin.getConfig().getDouble("Options.Sound." + soundName + ".pitch");

        player.playSound(player.getLocation(), sound, volume, pitch);

    }

}
