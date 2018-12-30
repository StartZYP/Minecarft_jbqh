package com.q44920040.bjqh.Cache;


import com.q44920040.bjqh.Data.qhFormulaData;
import com.q44920040.bjqh.Util.FileTool;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cache {
    public static Map<String, qhFormulaData> qhMap = new HashMap();

    public Cache() {
    }

    public static void loadFormula() {
        qhMap.clear();
        Bukkit.getConsoleSender().sendMessage("已加载的配置文件：");
        Iterator iterator = FileTool.loadConfigs().iterator();

        while(iterator.hasNext()) {
            FileConfiguration config = (FileConfiguration)iterator.next();
            Iterator iterator1 = config.getKeys(false).iterator();

            while(iterator1.hasNext()) {
                String key = (String)iterator1.next();
                qhFormulaData qld = new qhFormulaData(key);
                Bukkit.getConsoleSender().sendMessage(key);
                if (qld.load(config.getConfigurationSection(key))) {
                    qhMap.put(key, qld);
                } else {
                    Bukkit.getLogger().info("§c强化配置错误!错误节点:" + key);
                }
            }
        }

    }

    public static qhFormulaData getQHFormula(String key) {
        return (qhFormulaData)qhMap.get(key);
    }
}
