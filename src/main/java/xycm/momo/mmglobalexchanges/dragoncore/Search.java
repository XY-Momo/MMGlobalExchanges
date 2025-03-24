package xycm.momo.mmglobalexchanges.dragoncore;

import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

/**
 * 搜索界面龙核事件监听器
 */
public class Search implements Listener {
    @EventHandler
    public void onSearch(CustomPacketEvent event) {
        if (event.getIdentifier().equals("mmglobalexchanges")) {
            if (event.getData().get(0).equals("search")) {
                MMGlobalExchanges.market.setFilter(event.getPlayer(), event.getData().get(1));
                MMGlobalExchanges.market.setPage(event.getPlayer(), 1);
                MMGlobalExchanges.market.open(event.getPlayer());
            }
        }
    }
}
