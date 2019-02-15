package com.q44920040.bjqh.Util;


import com.q44920040.bjqh.QH;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Eco {
    public static Economy econ = null;

    public static boolean setupEconomy() {
        if (QH.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = QH.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = (Economy)rsp.getProvider();
        return econ != null;
    }

    public static double look(String pn) {
        return econ.getBalance(pn);
    }

    public static boolean canpay(String pn, double price) {
        return econ.has(pn, price);
    }

    public static boolean give(String pn, double price) {
        return econ.depositPlayer(pn, price).transactionSuccess();
    }

    public static boolean pay(String pn, double price) {
        if (econ.has(pn, price)) {
            return econ.withdrawPlayer(pn, price).transactionSuccess();
        }
        return false;
    }
}
