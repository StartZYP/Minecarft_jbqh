package com.q44920040.bjqh.Data;


import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.item.RPGItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class qhFormulaData {
    private String qhName;
    public List<qhData> qhLevel = new ArrayList<qhData>();

    public qhFormulaData(String key) {
        this.qhName = key;
    }

    public boolean load(ConfigurationSection config) {
        try {
            this.qhLevel.add(new qhData(config.getString("input"), this));
            for (String key : config.getConfigurationSection("levels").getKeys(false)) {
                qhData qd = new qhData(config.getString("levels." + key + ".succ"), config.getString("levels." + key + ".fail"), key, config.getInt("levels." + key + ".Cost"), config.getInt("levels." + key + ".eachSucc"), this);
                this.qhLevel.add(qd);
            }
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public qhData getQHData(RPGItem rpgitem) {
        for (qhData qd : this.qhLevel) {
            if (!qd.sucRPGItem.getName().equals(rpgitem.getName())) continue;
            return qd;
        }
        return null;
    }
}
