package me.gushel.resourcepackenforcer.listeners;

import me.gushel.resourcepackenforcer.ResourcePackEnforcer;
import me.gushel.resourcepackenforcer.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.logging.Logger;

public class PlayerJoin implements Listener {

    private int repeatCount;

    private int taskID;

    @EventHandler
    @SuppressWarnings( "deprecation" )
    public void onPlayerJoin(PlayerJoinEvent event){
        Logger log = ResourcePackEnforcer.getInstance().getLogger();
        Player player = event.getPlayer();
        ResourcePackEnforcer.useRP.remove(player);
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();

        if (Objects.equals(config.getString("settings.resourcepack"), "NO_LINK") && config.getBoolean("settings.no-resourcepack-link-warning")){
            player.sendMessage(Util.papiColor(player,config.getString("messages.no-resourcepack-in-config")));
            log.warning("You didn't set any resourcepack link in the config file! Please do that and reload the plugin in order to send a resourcepack to players!");
            return;
        }
        if (player.hasPermission(Objects.requireNonNull(config.getString("settings.bypass-permission")))) return;
        repeatCount = config.getInt("countdown-timer.timer")+config.getInt("countdown-timer.time-before-timer")+1;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ResourcePackEnforcer.getInstance(), () -> {
            --repeatCount;
            if (repeatCount<config.getInt("countdown-timer.timer")+1) {
                if (config.getBoolean("countdown-timer.titles.enable"))
                    player.sendTitle(Util.papiColor(player, config.getString("countdown-timer.titles.title")).replace("%time%", String.valueOf(repeatCount))
                        , Util.papiColor(player, config.getString("countdown-timer.titles.subtitle")).replace("%time%", String.valueOf(repeatCount)));
                if (config.getBoolean("countdown-timer.repeating-message")){
                    for (String s : config.getStringList("messages.repeating-message")){
                        player.sendMessage(Util.papiColor(player,Util.color(s).replace("%time%", String.valueOf(repeatCount))));
                    }
                }

            }
            if (repeatCount==0) cancelTask(taskID,player);
        }, 0, 20);
        if (config.getBoolean("effects.enable-blindness")) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,9999,200));
        if (config.getBoolean("effects.enable-slowness")) player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,9999,200));
    }

    private void cancelTask(int taskID,Player player){
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();
        Bukkit.getScheduler().cancelTask(taskID);
        player.setResourcePack(Objects.requireNonNull(config.getString("settings.resourcepack")));
    }

}
