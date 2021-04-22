package me.michaeldev.ExperienceLuckyBlocks;

import me.michaeldev.ExperienceLuckyBlocks.commands.CommandManager;
import me.michaeldev.ExperienceLuckyBlocks.events.PlayerBreakEvent;
import me.michaeldev.ExperienceLuckyBlocks.events.PlayerjoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ExperienceLuckyBlocks extends JavaPlugin {

    private static File fileglobalDB;
    private static FileConfiguration globalDBconfig;

    private static File fileDefPlTest;
    private static FileConfiguration DefPlTestconfig;

    @Override
    public void onEnable() {

        //Load config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();


        //global.yml
        fileglobalDB = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks").getDataFolder(), "StatsDB/global.yml");
        globalDBconfig = YamlConfiguration.loadConfiguration(fileglobalDB);

        if(!fileglobalDB.exists()){
            globalDBconfig.set("TotalBlockNormal", 0);
            globalDBconfig.set("TotalBlockVip", 0);
            try {
                globalDBconfig.save(fileglobalDB);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //DefaultPlayerTest.yml
        fileDefPlTest = new File(Bukkit.getServer().getPluginManager().getPlugin("ExperienceLuckyBlocks").getDataFolder(), "StatsDB/Players/DefaultPlayerTest.yml");
        DefPlTestconfig = YamlConfiguration.loadConfiguration(fileDefPlTest);

        DefPlTestconfig.set("TotalBlockNormal", 0);
        DefPlTestconfig.set("TotalBlockVip", 0);

        try {
            DefPlTestconfig.save(fileDefPlTest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Command Manager [ /gl <command> ]
        getCommand("gl").setExecutor(new CommandManager());

        //Events

        getServer().getPluginManager().registerEvents(new PlayerjoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerBreakEvent(), this);

        //Console
        getLogger().info("\n" +
                "---------------------------\n" +
                "Experience LuckyBlocks Loaded Successfully!" +
                "\n---------------------------");
    }
}
