package moe.gabriella.playertimestopper;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerTimeStopper extends JavaPlugin implements Listener {

    FileConfiguration config;

    @Override
    public void onEnable() {
        config = this.getConfig();

        Util.info("Starting PTS...");

        if (config.getBoolean("enabled")) {
            this.getServer().getPluginManager().registerEvents(this, this);
            Util.info("Registered events! Plugin is enabled.");
        } else {
            Util.info("Plugin is disabled in config. To enable, set the \"enabled\" value to \"true\"!");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        toggleTime(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.getServer().getOnlinePlayers().size() == 0) {
            toggleTime(false);
        }
    }

    private void toggleTime(boolean result) {
        for (String s : config.getStringList("worlds")) {
            World w = this.getServer().getWorld(s);
            if (w == null) {
                Util.error("World " + s + " does not exist! Please check your config file.");
                continue;
            }

            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, result);
        }
    }
}
