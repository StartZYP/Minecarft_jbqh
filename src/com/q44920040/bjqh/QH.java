package com.q44920040.bjqh;


import com.q44920040.bjqh.Cache.Cache;
import com.q44920040.bjqh.GUI.MainGui;
import com.q44920040.bjqh.Util.Eco;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class QH extends JavaPlugin {
    private static QH instance;
    public static String qhLore = "可强化:";
    public static String confirm_button;
    public static String show_button;
    public static String oi;
    public static String tishenglore;

    public QH() {
    }

    public static QH getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new EventListeners(), this);
        this.load();
        Eco.setupEconomy();
        tishenglore = getConfig().getString("chance");
        System.out.println(tishenglore);
    }

    public void load() {
        this.saveDefaultConfig();
        this.reloadConfig();
        FileConfiguration config = this.getConfig();
        confirm_button = config.getString("button.confirm").replace("&", "§");
        show_button = config.getString("button.show").replace("&", "§");
        oi = config.getString("other.item");
        Cache.loadFormula();
        MainGui.loadGui();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§a/jb open - 打开金币强化GUI");
            return true;
        } else {
            if (args.length == 1) {
                if (args[0].equals("open")) {
                    if (sender instanceof Player) {
                        MainGui.open((Player)sender);
                        return true;
                    }
                } else if (args[0].equals("reload") && sender.isOp()) {
                    this.load();
                    sender.sendMessage("§a重载成功");
                    return true;
                }
            }

            return false;
        }
    }
}

