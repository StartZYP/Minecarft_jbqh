package com.q44920040.bjqh.Util;


import com.q44920040.bjqh.QH;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileTool {
    public static Set<FileConfiguration> loadConfigs() {
        String[] qhids;
        HashSet<FileConfiguration> temps = new HashSet<FileConfiguration>();
        File file = new File(QH.getInstance().getDataFolder() + File.separator + "data");
        if (!file.exists()) {
            file.mkdir();
        }
        for (String qhid : qhids = file.list()) {
            File ifile = new File(QH.getInstance().getDataFolder() + File.separator + "data" + File.separator + qhid);
            temps.add((FileConfiguration)YamlConfiguration.loadConfiguration((File)ifile));
        }
        return temps;
    }
}

