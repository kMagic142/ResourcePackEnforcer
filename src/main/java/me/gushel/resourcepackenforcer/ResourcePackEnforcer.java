package me.gushel.resourcepackenforcer;

import me.gushel.resourcepackenforcer.commands.MainCommand;
import me.gushel.resourcepackenforcer.listeners.PlayerJoin;
import me.gushel.resourcepackenforcer.listeners.PlayerResourcePackStatus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class ResourcePackEnforcer extends JavaPlugin {

    private static ResourcePackEnforcer plugin;

    public static List<Player> useRP;

    @Override
    public void onEnable() {
        Logger log = this.getLogger();
        FileConfiguration config = this.getConfig();
        useRP = new ArrayList<>();
        saveDefaultConfig();
        Config.setup();
        Config.get().options().copyDefaults(true);
        Config.save();
        plugin = this;
        this.getCommand("resourcepackenforcer").setExecutor(new MainCommand());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerResourcePackStatus(), this);
        new Placeholders(this).register();
        if (config.getString("settings.resourcepack").equals("NO_LINK")){
            log.warning("You didn't set any resourcepack link in the config file! Please do that and reload the plugin in order to send a resourcepack to players!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ResourcePackEnforcer getInstance(){
        return plugin;
    }

}
