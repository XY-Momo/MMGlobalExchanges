package xycm.momo.mmglobalexchanges.listener.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
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

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        int rawSlot = event.getRawSlot();
        int inventorySize = event.getInventory().getSize();
        if (event.getCurrentItem().getType().equals(Material.AIR)) {
            return;
        }
        // 检查玩家是否从 Market 界面拿取物品
        if (rawSlot < inventorySize) {

            int slot = event.getSlot();
            if (slot < 45) {
                if (clickedItem.hasItemMeta()) {
                    if (clickedItem.getItemMeta().hasDisplayName()) {
                        MMGlobalExchanges.market.setFilter(player, clickedItem.getItemMeta().getDisplayName());
                    } else {
                        MMGlobalExchanges.market.setFilter(player, clickedItem.getType().name());
                    }
                } else {
                    MMGlobalExchanges.market.setFilter(player, clickedItem.getType().name());
                }
                MMGlobalExchanges.market.open(player);
            } else {
                switch (slot) {
                    case 46:
                        MMGlobalExchanges.market.setFilter(player, null);
                        MMGlobalExchanges.market.open(player);
                        break;
                    case 47:
                        MMGlobalExchanges.search.setPage(player, 1);
                        MMGlobalExchanges.search.open(player);
                        break;
                    case 48:
                        MMGlobalExchanges.search.setPage(player, MMGlobalExchanges.search.getPage(player) - 1);
                        MMGlobalExchanges.search.open(player);
                        break;
                    case 50:
                        MMGlobalExchanges.search.setPage(player, MMGlobalExchanges.search.getPage(player) + 1);
                        MMGlobalExchanges.search.open(player);
                        break;
                    case 51:
                        MMGlobalExchanges.search.setPage(player, (MMGlobalExchanges.search.getSize() - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_search_max") + 1);
                        MMGlobalExchanges.search.open(player);
                        break;
                }
            }
        }
    }
}
