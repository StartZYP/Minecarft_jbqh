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
import java.util.Set;

public class EventListeners implements Listener {
    Set<String> lists = new HashSet();

    public EventListeners() {
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player)e.getPlayer();
            Inventory guiinv;
            if (this.lists.contains(p.getName()) && e.getInventory().getTitle().equals(MainGui.title)) {
                guiinv = e.getInventory();
                ItemStack qhitem = guiinv.getItem(13);
                ItemStack bsitem = guiinv.getItem(37);
                if (qhitem!=null&&qhitem.getType()!=Material.AIR){
                    p.getInventory().addItem(qhitem);
                    guiinv.setItem(13,new ItemStack(Material.AIR));
                }
                if (bsitem!=null&&bsitem.getType()!=Material.AIR){
                    p.getInventory().addItem(bsitem);
                    guiinv.setItem(37,new ItemStack(Material.AIR));
                }
                p.updateInventory();
            } else if (e.getInventory().getTitle().equals("强化完毕") && e.getPlayer() instanceof Player) {
                guiinv = e.getInventory();
                ItemStack item = guiinv.getItem(13);
                guiinv.setItem(37,new ItemStack(Material.AIR));
                if (item != null) {
                    p.getWorld().dropItem(p.getLocation().add(1.0D, 0.0D, 0.0D), item);
                }
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
                        MainGui.confirm(inv, p, 0);
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
        if (slot < 54 && !MainGui.no_check.contains(slot)&&slot!=37) {
            if (e.getClick() == ClickType.SHIFT_LEFT) {
                p.sendMessage("§c异常操作,请重新打开");
                e.setCancelled(true);
                p.closeInventory();
                return false;
            }
            e.setCancelled(true);
        }
        return true;
    }
}