package me.gushel.resourcepackenforcer.commands;

import me.gushel.resourcepackenforcer.ResourcePackEnforcer;
import org.bukkit.configuration.file.FileConfiguration;

public class Reload {

    public static void onCommand(){
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();
        ResourcePackEnforcer.getInstance().reloadConfig();
    }

}
