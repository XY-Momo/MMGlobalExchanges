package xycm.momo.mmglobalexchanges.thread;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

public class LaunchRecordInfo {
    public static void freshLaunchRecordInfo(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                MMGlobalExchanges.launchRecord.setInfo(player, " ");
                MMGlobalExchanges.launchRecord.open(player);
            }
        }.runTaskLater(MMGlobalExchanges.instance, MMGlobalExchanges.instance.getConfig().getInt("info_delay"));
    }

    public static void freshBlackLaunchRecordInfo(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                MMGlobalExchanges.blackLaunchRecord.setInfo(player, " ");
                MMGlobalExchanges.blackLaunchRecord.open(player);
            }
        }.runTaskLater(MMGlobalExchanges.instance, MMGlobalExchanges.instance.getConfig().getInt("info_delay"));
    }
}
