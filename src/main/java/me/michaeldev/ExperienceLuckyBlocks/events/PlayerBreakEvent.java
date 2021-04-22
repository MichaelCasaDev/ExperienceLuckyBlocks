package me.michaeldev.ExperienceLuckyBlocks.events;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;



public class PlayerBreakEvent implements Listener {

    /****************************
     Databases
     ****************************/
    private static File fileplayers;
    private static FileConfiguration playersconfig;

    private static File fileglobalDB;
    private static FileConfiguration globalDBconfig;


    //Errors
    private static boolean error_in_randomgennormal;
    private static boolean error_in_randomgenvip;


    //Config
    final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks");
    final FileConfiguration config = plugin.getConfig();



    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent e) throws IOException {
        Player player = e.getPlayer();
        Material block = e.getBlock().getType();
        String playerUUID = String.valueOf(player.getUniqueId());

        //For player DB
        fileplayers = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks").getDataFolder(), "StatsDB/Players/" + playerUUID + ".yml");
        playersconfig = YamlConfiguration.loadConfiguration(fileplayers);


        //Global DB
        fileglobalDB = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks").getDataFolder(), "StatsDB/global.yml");
        globalDBconfig = YamlConfiguration.loadConfiguration(fileglobalDB);

        if(block.name().equals(config.getString("LuckyBlockID_Normal"))){
            //Set the block break as AIR so the player can't pick up them
            e.getBlock().setType(Material.AIR);


            NormalLuckyBlock(player);
        } else if(block.name().equals(config.getString("LuckyBlockID_Vip")) && player.hasPermission("gl.vip")){
            //Set the block break as AIR so the player can't pick up them
            e.getBlock().setType(Material.AIR);


            VipLuckyBlock(player);
        } else if(block.name().equals(config.getString("LuckyBlockID_Vip")) && !player.hasPermission("gl.vip")){
            e.setCancelled(true);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("NoVipLucky")));
        }

        globalDBconfig.save(fileglobalDB);
        playersconfig.save(fileplayers);

    }

    /****************************

     Normal and Vip Functions

     ****************************/

    //Adding probability to blocks

    private void NormalLuckyBlock(Player player) {
        String rewards_Null = config.getString("Rewards_null");
        int max_rew = config.getInt("Rewards_total_Normal");

        int random = getRandom(1, max_rew, player);
        boolean enabled = config.getBoolean("Rewards_Normal."+ random + ".enabled");


        if(!error_in_randomgennormal){
            if(enabled){
                String commandx = config.getString("Rewards_Normal." + random + ".command");
                String message_tosand = config.getString("Rewards_Normal." + random + ".message");


                player.sendMessage(ChatColor.translateAlternateColorCodes('&',message_tosand.replace("{player}", player.getDisplayName())));

                //Increment the stats
                playersconfig.set("TotalBlockNormal", (playersconfig.getInt("TotalBlockNormal") + 1));
                globalDBconfig.set("TotalBlockNormal", (globalDBconfig.getInt("TotalBlockNormal") + 1));

                //Execute command
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = commandx.replace("{player}", player.getName());
                Bukkit.dispatchCommand(console, command);
            } else { //Found null
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', rewards_Null));
            }
        }
    }

    private void VipLuckyBlock(Player player){
        String rewards_Null = config.getString("Rewards_null");
        int max_rew = config.getInt("Rewards_total_Vip");

        int random = getRandom(1, max_rew, player);
        boolean enabled = config.getBoolean("Rewards_Vip."+ random + ".enabled");

        if(!error_in_randomgenvip){
            if(enabled){
                String commandx = config.getString("Rewards_Vip." + random + ".command");
                String message_tosand = config.getString("Rewards_Vip." + random + ".message");


                player.sendMessage(ChatColor.translateAlternateColorCodes('&',message_tosand.replace("{player}", player.getDisplayName())));

                //Increment the stats
                playersconfig.set("TotalBlockVip", (playersconfig.getInt("TotalBlockVip") + 1));
                globalDBconfig.set("TotalBlockVip", (globalDBconfig.getInt("TotalBlockVip") + 1));

                //Execute command
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = commandx.replace("{player}", player.getName());
                Bukkit.dispatchCommand(console, command);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', rewards_Null));
            }
        }
    }

    /***************************************************************************************************************
     Random int generator from 2 different int [ require to have a player to send custom error messages ]
     ***************************************************************************************************************/

    public static int getRandom(int min, int max, Player p) {
        if (min > max) {
            error_in_randomgennormal = true;
            error_in_randomgenvip = true;
            p.sendMessage(ChatColor.RED + "[ ExperienceLuckyBlocks ]  Error check the console or contact the server admin!");
        }
        return (int) ( (long) min + Math.random() * ((long)max - min + 1));
    }
}
