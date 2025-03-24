package xycm.momo.mmglobalexchanges.event.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.event.market.SelectItemListener;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackMarket;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackPersonalInfo;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackSearch;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackSelectItem;
import xycm.momo.mmglobalexchanges.ui.market.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 黑市界面监听器
 */
public class BlackMarketListener implements Listener {
    @EventHandler
    public void clickBlackMarket(InventoryClickEvent event) {
        BlackMarket blackMarket = MMGlobalExchanges.blackMarket;
        if (!event.getInventory().getTitle().equals(blackMarket.getTitle())) {
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
        // 检查玩家是否从 Market 界面拿取物品
        if (rawSlot < inventorySize) {

            String displayName = clickedItem.getItemMeta().getDisplayName();
            int slot = event.getSlot();

            if (slot < 45) {
                if (event.getCurrentItem().getType().equals(Material.AIR)) {
                    return;
                }
                ItemStack item = event.getCurrentItem();
                List<String> lore = item.getItemMeta().getLore();
                int id = Integer.parseInt(lore.get(lore.size() - 2).split(" ")[1]);
                int price = Integer.parseInt(lore.get(lore.size() - 1).split(" ")[1]);
                Map<Integer, Integer> good = new HashMap<>();
                good.put(id, price);
                MMGlobalExchanges.blackPurchase.put(player.getName(), good);
                PurchaseConfirm purchaseConfirm = new PurchaseConfirm(MMGlobalExchanges.instance.getConfig().getString("black_market.confirm"));
                purchaseConfirm.open(player);
            }
            if (displayName == null) {return;}
            switch (slot) {
                case 45:
                    BlackSelectItemListener.selectReturnInfo.put(player.getName(), "市场");
                    BlackSelectItem selectItem = new BlackSelectItem(MMGlobalExchanges.instance.getConfig().getString("black_market.select"));
                    selectItem.setInfo(player, " ");
                    selectItem.open(player);
                    break;
                case 46:
                    BlackSearch search = new BlackSearch(MMGlobalExchanges.instance.getConfig().getString("black_market.search"));
                    search.open(player);
                    break;
                case 47:
                    blackMarket.setPage(player, 1);
                    blackMarket.open(player);
                    break;
                case 48:
                    blackMarket.setPage(player, blackMarket.getPage(player) - 1);
                    blackMarket.open(player);
                    break;
                case 49:
                    BlackPersonalInfo personalInfo = new BlackPersonalInfo(MMGlobalExchanges.instance.getConfig().getString("black_market.personal.name"));
                    personalInfo.open(player);
                    break;
                case 50:
                    blackMarket.setPage(player, blackMarket.getPage(player) + 1);
                    blackMarket.open(player);
                    break;
                case 51:
                    blackMarket.setPage(player, (MMGlobalExchanges.blackMarketFile.getSize(player) - 1) / MMGlobalExchanges.instance.getConfig().getInt("black_market_max") + 1);
                    blackMarket.open(player);
                    break;
                case 53:
                    blackMarket.close(player);
                    break;
            }
        } else {
            event.setCancelled(true);
        }
    }
}
