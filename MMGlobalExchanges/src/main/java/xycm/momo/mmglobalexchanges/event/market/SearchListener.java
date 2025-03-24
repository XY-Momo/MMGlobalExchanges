package xycm.momo.mmglobalexchanges.event.market;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

/**
 * 搜索界面监听器
 */
public class SearchListener implements Listener {
    @EventHandler
    public void clickSearch(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(MMGlobalExchanges.instance.getConfig().getString("market.search"))) {
            return;
        }
        event.setCancelled(true);
    }
}
