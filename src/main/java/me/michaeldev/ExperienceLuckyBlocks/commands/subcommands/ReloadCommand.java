package me.michaeldev.ExperienceLuckyBlocks.commands.subcommands;

import me.michaeldev.ExperienceLuckyBlocks.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Ricarica il config del plugin!";
    }

    @Override
    public String getSyntax() {
        return "/gl reload";
    }




    @Override
    public void perform(Player player, String[] args) {
        //Main Config

        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks");
        final FileConfiguration config = plugin.getConfig();


        if(player.hasPermission("gl.reload")){
            //FILEs to reload

            plugin.reloadConfig();

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Reload_Message") ));

        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ReloadNoPerms") ));

        }
    }
}
