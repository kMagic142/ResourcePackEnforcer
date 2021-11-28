package me.gushel.resourcepackenforcer;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {

    public static String color (String string){
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static String papiColor (Player player,String string){
        return PlaceholderAPI.setPlaceholders(player,ChatColor.translateAlternateColorCodes('&',string));
    }

    public static String papi (Player player, String string){
        return PlaceholderAPI.setPlaceholders(player,string);
    }

}
