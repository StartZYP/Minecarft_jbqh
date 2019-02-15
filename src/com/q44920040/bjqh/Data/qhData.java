package com.q44920040.bjqh.Data;


import think.rpgitems.api.RPGItems;
import think.rpgitems.item.RPGItem;

public class qhData {
    public RPGItem sucRPGItem;
    public RPGItem failRPGItem;
    public int Chance;
    public int Cost;
    public int level;
    qhFormulaData qld;

    public qhData(String suc, String fail, String level, int Cost, int Chance, qhFormulaData qld) throws Exception {
        this.failRPGItem = this.getRPGItem(RPGItems.getRPGItemByName((String)fail));
        this.sucRPGItem = this.getRPGItem(RPGItems.getRPGItemByName((String)suc));
        this.Cost = Cost;
        this.Chance = Chance;
        this.level = Integer.parseInt(level);
        this.qld = qld;
    }

    public qhData(String str, qhFormulaData qld) throws Exception {
        this.level = 0;
        this.sucRPGItem = this.getRPGItem(RPGItems.getRPGItemByName((String)str));
        this.qld = qld;
    }

    private RPGItem getRPGItem(RPGItem rpgitem) throws Exception {
        if (rpgitem == null) {
            throw new Exception();
        }
        return rpgitem;
    }

    public qhData getNextQHData() {
        int nextlevel = this.level + 1;
        if (this.qld.qhLevel.size() > nextlevel) {
            return this.qld.qhLevel.get(nextlevel);
        }
        return null;
    }
}
