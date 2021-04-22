package me.michaeldev.ExperienceLuckyBlocks.commands.subcommands;

import me.michaeldev.ExperienceLuckyBlocks.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class StatsCommand extends SubCommand {

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Ricevi le statistiche di un singolo player oppure di tutto il server!";
    }

    @Override
    public String getSyntax() {
        return "/gl stats < null - PlayerName >";
    }


    //StatsDB/global.yml
    private static File fileglobal;
    private static FileConfiguration globalconfig;

    //Player Stats
    private static File fileplayer;
    private static FileConfiguration playerconfig;

    Player playerinfoarg;

    @Override
    public void perform(Player player, String[] args) throws IOException {

        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks");
        final FileConfiguration config = plugin.getConfig();


        fileglobal = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks").getDataFolder(), "StatsDB/global.yml");
        globalconfig = YamlConfiguration.loadConfiguration(fileglobal);

        if(player.hasPermission("gl.stats")){
            if(args.length > 1) { //Stats <player>

                playerinfoarg = Bukkit.getPlayer(args[1]);

                if(playerinfoarg != null){ //Player Exists

                    String playerUUID = String.valueOf(playerinfoarg.getUniqueId());

                    fileplayer = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks").getDataFolder(), "StatsDB/Players/" + playerUUID + ".yml");
                    playerconfig = YamlConfiguration.loadConfiguration(fileplayer);

                    player.sendMessage("----------------------------");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("InfoHeaderPlayer")));
                    player.sendMessage("");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Lucky Block Normal ID: &f" + config.getString("LuckyBlockID_Normal")));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Lucky Block Vip ID: &f" + config.getString("LuckyBlockID_Vip")));
                    player.sendMessage("");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Lucky Block break: &f" + (playerconfig.getInt("TotalBlockVip") + playerconfig.getInt("TotalBlockNormal"))));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Lucky Block Normal break: &f" + playerconfig.getInt("TotalBlockNormal")));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Lucky Block Vip break: &f" + playerconfig.getInt("TotalBlockVip")));
                    player.sendMessage("----------------------------");

                } else { //Player not exists
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("InfoPlayerNotExist") ));
                }
            } else { // Stats Global

                player.sendMessage("----------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("InfoHeader")));
                player.sendMessage("");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Lucky Block Normal ID: &f" + config.getString("LuckyBlockID_Normal")));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Lucky Block Vip ID: &f" + config.getString("LuckyBlockID_Vip")));
                player.sendMessage("");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Lucky Block break: &f" + (globalconfig.getInt("TotalBlockVip") + globalconfig.getInt("TotalBlockNormal"))));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Lucky Block Normal break: &f" + globalconfig.getInt("TotalBlockNormal")));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Lucky Block Vip break: &f" + globalconfig.getInt("TotalBlockVip")));
                player.sendMessage("----------------------------");
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("InfoNoPerms") ));
        }
    }
}
