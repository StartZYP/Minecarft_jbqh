package com.q44920040.bjqh.GUI;


import com.q44920040.bjqh.Cache.Cache;
import com.q44920040.bjqh.Data.qhData;
import com.q44920040.bjqh.Data.qhFormulaData;
import com.q44920040.bjqh.QH;
import com.q44920040.bjqh.Util.Eco;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import think.rpgitems.api.RPGItems;
import think.rpgitems.item.RPGItem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainGui {
    public static String title1 = "§0§l强化中..";
    public static String title = "§0§l金币强化";
    static ItemStack[] items = new ItemStack[54];
    static int qh = 13;
    public static Set<Integer> no_check = new HashSet();
    public static int confirm_button = 43;
    public static int show_button = 40;

    public MainGui() {
    }

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, title);
        inv.setContents(items);
        p.openInventory(inv);
    }

    public static void updataShowButton(Inventory inv, Player p) {
        int succ = getSucc(inv, p);
        int nowc = getNowc(inv, p);
        ItemStack itemStack = inv.getItem(37);
        if (itemStack!=null&&itemStack.getType()!=Material.AIR) {
            ItemMeta Meta37 = itemStack.getItemMeta();
            if (Meta37.getLore()!=null&&Meta37.getLore().size()>0){
                for (String line:Meta37.getLore()){
                    if (line.contains(QH.tishenglore)){
                        String succstring = getSubString(line,"[","]");
                        succ+=Integer.parseInt(succstring);
                    }
                }
            }
        }
        if (succ > 100) {
            succ = 100;
        }
        String nextc = getNextc(inv, p);
        ItemStack show = new ItemStack(Material.SIGN);
        ItemMeta im = show.getItemMeta();
        if (nextc == null) {
            im.setDisplayName(QH.show_button.replace("%nowc", String.valueOf(nowc)).replace("%nextc", String.valueOf("§c没有下次啦!")).replace("%s", String.valueOf(succ)));
        } else {
            im.setDisplayName(QH.show_button.replace("%nowc", String.valueOf(nowc)).replace("%nextc", String.valueOf(nextc)).replace("%s", String.valueOf(succ)));
        }
        show.setItemMeta(im);
        inv.setItem(show_button, show);
    }

    public static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public static void confirm(Inventory inv, Player p, int i) {
        qhData nqd = getNextQHData(inv, p, 1);
        if (nqd != null) {
            int succ = nqd.Chance;

            qhData nqd1 = null;
            if (!Eco.pay(p.getName(), (double)nqd.Cost)) {
                p.sendMessage("§c强化所需金币不足！");
                return;
            }
            inv.setItem(37,null);
            nqd1 = getNextQHData(inv, p, 0);
            ItemStack itemStack = inv.getItem(37);
            if (itemStack!=null&&itemStack.getType()!=Material.AIR) {
                ItemMeta Meta37 = itemStack.getItemMeta();
                if (Meta37.getLore()!=null&&Meta37.getLore().size()>0){
                    for (String line:Meta37.getLore()){
                        if (line.contains(QH.tishenglore)){
                            String succstring = getSubString(line,"[","]");
                            succ+=Integer.parseInt(succstring);
                        }
                    }

                }
            }
            if (succ > 100) {
                succ = 100;
            }
            if (crit((double)succ / 100.0D)) {
                try {
                    (new Tx(p, QH.getInstance(), nqd1, 0)).start();
                } catch (Exception var8) {
                    Bukkit.getLogger().warning(var8.toString());
                }
            } else {
                try {
                    (new Tx(p, QH.getInstance(), nqd1, 1)).start();
                } catch (Exception var7) {
                    Bukkit.getLogger().warning(var7.toString());
                }
            }
        }

    }

    private static qhData getNextQHData(Inventory inv, Player p, int i) {
        RPGItem qhrpg = getRPGQH(inv);
        String name = null;
        if (qhrpg == null) {
            p.sendMessage("§c请放入可强化的强化武器/非RPGItem武器");
            return null;
        } else if (getName(inv) == null) {
            p.sendMessage("§c此物品不能被强化!");
            return null;
        } else {
            name = getName(inv);
            qhFormulaData qfd = Cache.getQHFormula(name);
            if (qfd == null) {
                p.sendMessage("§c允许强化武器不存在!");
                return null;
            } else {
                if (i == 0) {
                    inv.setItem(qh, null);
                }

                qhData nqd = qfd.getQHData(qhrpg).getNextQHData();
                if (nqd == null) {
                    p.sendMessage("§c你的武器已经强化到顶级");
                    return null;
                } else {
                    return nqd;
                }
            }
        }
    }

    private static int getSucc(Inventory inv, Player p) {
        int succ = 0;
        qhData nqd = getNextQHData(inv, p, 1);
        if (nqd != null) {
            succ = nqd.Chance;
            if (succ > 100) {
                succ = 100;
            }
        }

        return succ;
    }

    private static int getNowc(Inventory inv, Player p) {
        int a = 0;
        qhData nqd = getNextQHData(inv, p, 1);
        if (nqd != null) {
            a = nqd.Cost;
        }

        return a;
    }

    private static String getNextc(Inventory inv, Player p) {
        String a = null;
        ItemStack item = inv.getItem(qh);
        qhData nqd = getNextQHData(inv, p, 1);
        if (nqd != null) {
            if (nqd.getNextQHData() == null) {
                a = null;
            } else {
                a = String.valueOf(nqd.getNextQHData().Cost);
            }
        }

        return a;
    }

    public static boolean crit(double cr) {
        return Math.random() - cr < 0.0D;
    }

    private static RPGItem getRPGQH(Inventory inv) {
        ItemStack item = inv.getItem(qh);
        return item != null && item.getType() != Material.AIR ? RPGItems.toRPGItem(item) : null;
    }

    private static ItemStack setItemDisplay(int i) {
        FileConfiguration config = QH.getInstance().getConfig();
        String display = config.getString("button.button_" + i).replace("&", "§");
        ItemStack info = new ItemStack(Material.SAND);
        ItemMeta im = info.getItemMeta();
        im.setDisplayName(display);
        info.setItemMeta(im);
        return info;
    }

    private static String getName(Inventory inv) {
        ItemStack item = inv.getItem(qh);
        String name = null;
        if (item != null && item.getType() != Material.AIR && item.getAmount() == 1 && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            Iterator var3 = item.getItemMeta().getLore().iterator();

            while(var3.hasNext()) {
                String lore = (String)var3.next();
                if (lore.indexOf(QH.qhLore) > -1) {
                    String temp = lore.substring(lore.indexOf(QH.qhLore) + QH.qhLore.length());
                    name = temp;
                }
            }
        }

        return name;
    }

    public static void loadGui() {
        no_check.add(qh);

        for(int i = 0; i < 54; ++i) {
            if (!no_check.contains(i)) {
                ItemStack spent;
                ItemMeta im;
                if (i==0||i==1||i==2||i==6||i==7||i==8||i==9||i==10||i==11||i==15||i==16||i==17||i==18||i==19||i==20||i==24||i==25||i==26||i==30||i==31||i==32||i==39||i==40||i==41||i==48||i==49||i==50){
                    ItemStack info = new ItemStack(160, 1);
                    im = info.getItemMeta();
                    im.setDisplayName("§e请在框内放入强化武器!");
                    info.setItemMeta(im);
                    items[i] = info;
                }
                if (i==3||i==4||i==5||i==12||i==14||i==21||i==22||i==23){
                    ItemStack info = new ItemStack(160, 1,(short)2);
                    im = info.getItemMeta();
                    im.setDisplayName("§e请在框内放入强化武器!");
                    info.setItemMeta(im);
                    items[i] = info;
                }
                if (i==27||i==28||i==29||i==36||i==38||i==45||i==46||i==47){
                    ItemStack info = new ItemStack(160, 1,(short)4);
                    im = info.getItemMeta();
                    im.setDisplayName("§e请在框内放入强化武器!");
                    info.setItemMeta(im);
                    items[i] = info;
                }
                if (i==33||i==34||i==35||i==42||i==44||i==51||i==52||i==53){
                    ItemStack info = new ItemStack(160, 1,(short)14);
                    im = info.getItemMeta();
                    im.setDisplayName("§e请在框内放入强化武器!");
                    info.setItemMeta(im);
                    items[i] = info;
                }
                if (i == confirm_button) {
                    spent = new ItemStack(Material.ANVIL);
                    im = spent.getItemMeta();
                    im.setDisplayName(QH.confirm_button);
                    spent.setItemMeta(im);
                    items[i] = spent;
                } else if (i == show_button) {
                    spent = new ItemStack(Material.SIGN);
                    im = spent.getItemMeta();
                    im.setDisplayName(QH.show_button.replace("%s", "§e请点击更新").replace("%nowc", "§e获取失败").replace("%nextc", "§e获取失败"));
                    spent.setItemMeta(im);
                    items[i] = spent;
                }
            }
        }

    }
}