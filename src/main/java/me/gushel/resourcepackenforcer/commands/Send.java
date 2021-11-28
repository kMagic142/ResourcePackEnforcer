package me.gushel.resourcepackenforcer.commands;

import me.gushel.resourcepackenforcer.ResourcePackEnforcer;
import me.gushel.resourcepackenforcer.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class Send {

    public static void onCommand(Player player){
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();
        Logger log = ResourcePackEnforcer.getInstance().getLogger();
        if (ResourcePackEnforcer.useRP.contains(player)) ResourcePackEnforcer.useRP.remove(player);
        if (config.getString("settings.resourcepack").equals("NO_LINK") && config.getBoolean("settings.no-resourcepack-link-warning")){
            player.sendMessage(Util.papiColor(player,config.getString("messages.no-resourcepack-in-config")));
            log.warning("You didn't set any resourcepack link in the config file! Please do that in order to send a resourcepack to players!");
            return;
        }
        player.setResourcePack(config.getString("settings.resourcepack"));
    }


}
