package xycm.momo.mmglobalexchanges.thread;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

public class MailInfo {
    public static void freshMailInfo(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                MMGlobalExchanges.mail.setInfo(player, " ");
                MMGlobalExchanges.mail.open(player);
            }
        }.runTaskLater(MMGlobalExchanges.instance, MMGlobalExchanges.instance.getConfig().getInt("info_delay"));
    }

    public static void freshBlackMailInfo(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                MMGlobalExchanges.blackMail.setInfo(player, " ");
                MMGlobalExchanges.blackMail.open(player);
            }
        }.runTaskLater(MMGlobalExchanges.instance, MMGlobalExchanges.instance.getConfig().getInt("info_delay"));
    }
}
