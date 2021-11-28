package me.gushel.resourcepackenforcer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    private final ResourcePackEnforcer plugin;

    public Placeholders(ResourcePackEnforcer plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "resourcepackenforcer";
    }

    @Override
    public @NotNull String getAuthor() {
        return "gush3l";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();

        if (player == null) {
            return "";
        }

        Player player1 = player.getPlayer();

        if (identifier.equals("boolean")){
            if (ResourcePackEnforcer.useRP.contains(player1)) return "yes";
            return "no";
        }

        if (identifier.equals("using")){
            return String.valueOf(ResourcePackEnforcer.useRP.size());
        }

        if (identifier.equals("notusing")){
            return String.valueOf(Bukkit.getServer().getOnlinePlayers().size()-ResourcePackEnforcer.useRP.size());
        }

        if (identifier.equals("link")){
            return config.getString("settings.resourcepack");
        }

        return "";
    }

}
