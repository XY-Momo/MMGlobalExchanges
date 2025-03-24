package xycm.momo.mmglobalexchanges.dragoncore;

import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

/**
 * 黑市搜索界面龙核事件监听器
 */
public class BlackSearch implements Listener {
    @EventHandler
    public void onBlackSearch(CustomPacketEvent event) {
        if (event.getIdentifier().equals("mmglobalexchanges")) {
            if (event.getData().get(0).equals("blacksearch")) {
                MMGlobalExchanges.blackMarket.setFilter(event.getPlayer(), event.getData().get(1));
                MMGlobalExchanges.blackMarket.setPage(event.getPlayer(), 1);
                MMGlobalExchanges.blackMarket.open(event.getPlayer());
            }
        }
    }
}
