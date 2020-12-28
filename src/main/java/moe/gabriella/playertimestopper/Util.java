package moe.gabriella.playertimestopper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Util {

    public static void info(String msg) { Bukkit.getServer().getConsoleSender().sendMessage("" + ChatColor.WHITE + "[PlayerTimeStopper] " + msg); }
    public static void error(String msg) { Bukkit.getServer().getConsoleSender().sendMessage("" + ChatColor.RED + "[PlayerTimeStopper: ERROR] " + msg); }

}
