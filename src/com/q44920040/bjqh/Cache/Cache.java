package com.q44920040.bjqh.Cache;


import com.q44920040.bjqh.Data.qhFormulaData;
import com.q44920040.bjqh.Util.FileTool;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    public static Map<String, qhFormulaData> qhMap = new HashMap<String, qhFormulaData>();

    public static void loadFormula() {
        qhMap.clear();
        Bukkit.getConsoleSender().sendMessage("\u5df2\u52a0\u8f7d\u7684\u914d\u7f6e\u6587\u4ef6\uff1a");
        for (FileConfiguration config : FileTool.loadConfigs()) {
            for (String key : config.getKeys(false)) {
                qhFormulaData qld = new qhFormulaData(key);
                Bukkit.getConsoleSender().sendMessage(key);
                if (qld.load(config.getConfigurationSection(key))) {
                    qhMap.put(key, qld);
                    continue;
                }
                Bukkit.getLogger().info("\u00a7c\u5f3a\u5316\u914d\u7f6e\u9519\u8bef!\u9519\u8bef\u8282\u70b9:" + key);
            }
        }
    }

    public static qhFormulaData getQHFormula(String key) {
        return qhMap.get(key);
    }
}