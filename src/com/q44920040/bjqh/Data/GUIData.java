package com.q44920040.bjqh.Data;


import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUIData {
    public Inventory inv;
    public Player p;
    qhData nextQHData;

    public void updataQH() {
        System.out.println((Object)this.inv.getItem(13));
    }
}