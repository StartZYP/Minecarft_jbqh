package com.q44920040.bjqh.Data;


import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.item.RPGItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class qhFormulaData {
    private String qhName;
    public List<qhData> qhLevel = new ArrayList();

    public qhFormulaData(String key) {
        this.qhName = key;
    }

    public boolean load(ConfigurationSection config) {
        try {
            this.qhLevel.add(new qhData(config.getString("input"), this));
            Iterator var2 = config.getConfigurationSection("levels").getKeys(false).iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                qhData qd = new qhData(config.getString("levels." + key + ".succ"), config.getString("levels." + key + ".fail"), key, config.getInt("levels." + key + ".Cost"), config.getInt("levels." + key + ".eachSucc"), this);
                this.qhLevel.add(qd);
            }

            return true;
        } catch (Exception var5) {
            return false;
        }
    }

    public qhData getQHData(RPGItem rpgitem) {
        Iterator var2 = this.qhLevel.iterator();

        qhData qd;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            qd = (qhData)var2.next();
        } while(!qd.sucRPGItem.getName().equals(rpgitem.getName()));

        return qd;
    }
}
