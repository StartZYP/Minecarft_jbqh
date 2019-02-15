package com.q44920040.bjqh;


import com.q44920040.bjqh.GUI.MainGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EventListeners implements Listener {
    Set<String> lists = new HashSet();

    public EventListeners() {
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            ItemStack item;
            Inventory guiinv;
            Player p = (Player)e.getPlayer();
            if (this.lists.contains(p.getName()) && e.getInventory().getTitle().equals(MainGui.title)) {
                Inventory guiinv2 = e.getInventory();
                Iterator<Integer> localIterator = MainGui.no_check.iterator();
                while (localIterator.hasNext()) {
                    int i = localIterator.next();
                    ItemStack item2 = guiinv2.getItem(i);
                    if (item2 == null || item2.getType() == Material.AIR) continue;
                    p.getWorld().dropItem(p.getLocation().add(1.0, 0.0, 0.0), item2);
                }
            } else if (e.getInventory().getTitle().equals("\u5f3a\u5316\u5b8c\u6bd5") && e.getPlayer() instanceof Player && (item = (guiinv = e.getInventory()).getItem(22)) != null) {
                p.getWorld().dropItem(p.getLocation().add(1.0, 0.0, 0.0), item);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.lists.add(e.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.lists.remove(e.getPlayer().getName());
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            if (e.getAction() == InventoryAction.NOTHING) {
                return;
            }

            if (e.getInventory().getTitle().equals(MainGui.title)) {
                Inventory inv = e.getInventory();
                Player p = (Player)e.getWhoClicked();
                if (this.check(e, p)) {
                    int slot = e.getRawSlot();
                    if (slot == MainGui.show_button) {
                        MainGui.updataShowButton(inv, p);
                    } else if (slot == MainGui.confirm_button) {
                        MainGui.confirm(inv, p);
                    }
                }
            } else if (e.getInventory().getTitle().equals("强化中")) {
                if (e.getRawSlot() == 13&&e.getRawSlot()==37) {
                    e.setCancelled(false);
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }

    private boolean check(InventoryClickEvent e, Player p) {
        int slot = e.getRawSlot();
        if (slot < 54 && !MainGui.no_check.contains(slot)) {
            if (e.getClick() == ClickType.SHIFT_LEFT) {
                p.sendMessage("\u00a7c\u5f02\u5e38\u64cd\u4f5c,\u8bf7\u91cd\u65b0\u6253\u5f00");
                e.setCancelled(true);
                p.closeInventory();
                return false;
            }
            e.setCancelled(true);
        }
        return true;
    }
}