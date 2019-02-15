package com.q44920040.bjqh.GUI;


import com.q44920040.bjqh.Data.qhData;
import com.q44920040.bjqh.QH;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Tx
        extends Thread {
    private Inventory inv1;
    private QH plugin;
    private qhData qhd;
    private Player p;
    private String name = "\u5f3a\u5316\u4e2d";
    private int i = 0;
    private boolean stop = true;

    public Tx(Player p, QH plugin, qhData qhd, int d) {
        this.plugin = plugin;
        this.p = p;
        this.qhd = qhd;
        this.i = d;
    }

    public void stopThread(boolean stop) {
        this.stop = !stop;
    }

    @Override
    public void run() {
        this.name = "\u5f3a\u5316\u4e2d";
        this.inv1 = Bukkit.createInventory((InventoryHolder)this.p, (int)54, (String)this.name);
        int d = 0;
        ItemStack myitem = null;
        ItemStack myitem1 = new ItemStack(387, 1);
        for (int id = 0; id <= 1; ++id) {
            for (int i = 0; i <= 53; ++i) {
                if (d == 15) {
                    d = 0;
                }
                myitem = new ItemStack(160, 1, (short)d);
                ItemMeta im1 = myitem1.getItemMeta();
                ArrayList<String> lores1 = new ArrayList<String>();
                im1.setDisplayName("\u00a74\u6b66\u5668\u6b63\u5728[\u5f3a\u5316\u4e2d~~~~]");
                lores1.add("\u00a7e\u4f1a\u6210\u529f\u5417\uff1f\uff1f");
                im1.setLore(lores1);
                myitem1.setItemMeta(im1);
                ++d;
                ItemMeta im = myitem.getItemMeta();
                ArrayList<String> lores = new ArrayList<String>();
                im.setDisplayName("\u00a74\u6b63\u5728\u5f3a\u5316\uff01");
                lores.add("\u00a7c\u9ebb\u8ff7\u561b\u8ff7\u54c4~");
                im.setLore(lores);
                myitem.setItemMeta(im);
                if (i == 22) {
                    this.inv1.setItem(22, myitem1);
                } else {
                    try {
                        this.inv1.setItem(i, myitem);
                        Tx.sleep(150L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                this.p.openInventory(this.inv1);
            }
        }
        this.name = "\u5f3a\u5316\u5b8c\u6bd5";
        this.inv1 = Bukkit.createInventory((InventoryHolder)this.p, (int)54, (String)this.name);
        this.p.openInventory(this.inv1);
        this.name = "\u5f3a\u5316\u4e2d";
        if (this.i == 0) {
            this.p.sendMessage("\u00a74\u606d\u559c\u4f60\uff0c\u5f3a\u5316\u6210\u529f\uff0c\u8bf7\u53d6\u51fa\u6b66\u5668");
            this.inv1.setItem(22, this.qhd.sucRPGItem.toItemStack("zh_cn"));
            String mess = QH.getInstance().getConfig().getString("general.QHMess").replace("&", "\u00a7").replace("%p", this.p.getName()).replace("%n", this.qhd.sucRPGItem.getDisplay());
            Bukkit.broadcastMessage((String)mess);
        } else if (this.i == 1) {
            this.p.sendMessage("\u00a7e\u5f88\u62b1\u6b49\u5f3a\u5316\u5931\u8d25");
            this.inv1.setItem(22, this.qhd.failRPGItem.toItemStack("zh_cn"));
        }
    }
}
