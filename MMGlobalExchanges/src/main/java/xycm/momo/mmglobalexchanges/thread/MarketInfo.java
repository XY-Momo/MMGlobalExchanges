package xycm.momo.mmglobalexchanges.thread;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

public class MarketInfo {
    public static void freshMarketInfo(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                MMGlobalExchanges.market.setInfo(player, " ");
                MMGlobalExchanges.market.open(player);
            }
        }.runTaskLater(MMGlobalExchanges.instance, MMGlobalExchanges.instance.getConfig().getInt("info_delay"));
    }

    public static void freshBlackMarketInfo(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                MMGlobalExchanges.blackMarket.setInfo(player, " ");
                MMGlobalExchanges.blackMarket.open(player);
            }
        }.runTaskLater(MMGlobalExchanges.instance, MMGlobalExchanges.instance.getConfig().getInt("info_delay"));
    }
}
