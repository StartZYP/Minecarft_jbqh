package com.q44920040.bjqh.Util;


import com.q44920040.bjqh.QH;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileTool {
    public FileTool() {
    }

    public static Set<FileConfiguration> loadConfigs() {
        Set temps = new HashSet();
        File file = new File(QH.getInstance().getDataFolder() + File.separator + "data");
        if (!file.exists()) {
            file.mkdir();
        }

        String[] qhids = file.list();
        String[] var3 = qhids;
        int var4 = qhids.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String qhid = var3[var5];
            File ifile = new File(QH.getInstance().getDataFolder() + File.separator + "data" + File.separator + qhid);
            temps.add(YamlConfiguration.loadConfiguration(ifile));
        }

        return temps;
    }
}
