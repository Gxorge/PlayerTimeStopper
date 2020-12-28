package moe.gabriella.playertimestopper;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PlayerTimeStopper extends JavaPlugin implements Listener {

    FileConfiguration config;
    boolean verbose;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        config = this.getConfig();

        Util.info("Starting PTS...");

        verbose = config.getBoolean("verbose");

        if (config.getBoolean("enabled")) {
            this.getServer().getPluginManager().registerEvents(this, this);
            Util.info("Registered events! Plugin is enabled.");
            toggleTime(!(this.getServer().getOnlinePlayers().size() == 0), true);
        } else {
            Util.info("Plugin is disabled in config. To enable, set the \"enabled\" value to \"true\". All worlds in config have had their DDC's to true.");
            toggleTime(true, false);
        }
    }

    @Override
    public void onDisable() {
        Util.info("Thank you for using PTS, goodbye!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        toggleTime(true, this.getServer().getOnlinePlayers().size() == 1);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if ((this.getServer().getOnlinePlayers().size() -1) == 0) {
            toggleTime(false, true);
        }
    }

    private void toggleTime(boolean result, boolean output) {
        List<String> worlds = config.getStringList("worlds");
        int failed = 0;
        for (String s : worlds) {
            World w = this.getServer().getWorld(s);
            if (w == null) {
                Util.error("World " + s + " does not exist! Please check your config file.");
                failed++;
                continue;
            }

            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, result);
        }
        if (output && verbose) {
            Util.verbose("Do daylight cycle is now " + result + " in " + (worlds.size() - failed) + " world(s).");
            if (failed > 0)
                Util.error("Operation failed in " + failed + " world(s).");
        }
    }
}
