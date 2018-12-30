package com.q44920040.bjqh.GUI;


import com.q44920040.bjqh.Data.qhData;
import com.q44920040.bjqh.QH;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Tx extends Thread {
    private Inventory inv1;
    private QH plugin;
    private qhData qhd;
    private Player p;
    private String name = "强化中";
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

    public void run() {
        this.name = "强化中";
        this.inv1 = Bukkit.createInventory(this.p, 54, this.name);
        int d = 0;
        ItemStack myitem = null;
        ItemStack myitem1 = new ItemStack(387, 1, (short)0);

        for(int id = 0; id <= 1; ++id) {
            for(int i = 0; i <= 53; ++i) {
                if (d == 15) {
                    d = 0;
                }

                myitem = new ItemStack(160, 1, (short)d);
                ItemMeta im1 = myitem1.getItemMeta();
                List<String> lores1 = new ArrayList();
                im1.setDisplayName("§4武器正在[强化中~~~~]");
                lores1.add("§e会成功吗？？");
                im1.setLore(lores1);
                myitem1.setItemMeta(im1);
                ++d;
                ItemMeta im = myitem.getItemMeta();
                List<String> lores = new ArrayList();
                im.setDisplayName("§4正在强化！");
                lores.add("§c麻迷嘛迷哄~");
                im.setLore(lores);
                myitem.setItemMeta(im);
                if (i == 22) {
                    this.inv1.setItem(22, myitem1);
                } else {
                    try {
                        this.inv1.setItem(i, myitem);
                        sleep(60L);
                    } catch (InterruptedException var11) {
                    }
                }

                this.p.openInventory(this.inv1);
            }
        }

        this.name = "强化完毕";
        this.inv1 = Bukkit.createInventory(this.p, 54, this.name);
        this.p.openInventory(this.inv1);
        this.name = "强化中";
        if (this.i == 0) {
            this.p.sendMessage("§4恭喜你，强化成功，请取出武器");
            this.inv1.setItem(22, this.qhd.sucRPGItem.toItemStack("zh_cn"));
            String mess = QH.getInstance().getConfig().getString("general.QHMess").replace("&", "§").replace("%p", this.p.getName()).replace("%n", this.qhd.sucRPGItem.getDisplay());
            Bukkit.broadcastMessage(mess);
        } else if (this.i == 1) {
            this.p.sendMessage("§e很抱歉强化失败");
            this.inv1.setItem(22, this.qhd.failRPGItem.toItemStack("zh_cn"));
        }

    }
}
