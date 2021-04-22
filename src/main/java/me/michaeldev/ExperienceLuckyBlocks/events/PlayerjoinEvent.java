package me.michaeldev.ExperienceLuckyBlocks.events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class PlayerjoinEvent implements Listener {

    private static File fileplayers;
    private static FileConfiguration playersconfig;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws InterruptedException, IOException {
        Player player = e.getPlayer();

        String playerUUID = String.valueOf(player.getUniqueId());

        fileplayers = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks").getDataFolder(), "StatsDB/Players/" + playerUUID + ".yml");
        playersconfig = YamlConfiguration.loadConfiguration(fileplayers);
        
        if (!fileplayers.exists()) {
            try {
                fileplayers.createNewFile();
                playersconfig.set("TotalBlockNormal", 0);
                playersconfig.set("TotalBlockVip", 0);

                playersconfig.save(fileplayers);
            } catch (IOException es) {
                //owww
            }
        }
    }
}
