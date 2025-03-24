package xycm.momo.mmglobalexchanges.event.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.market.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 市场界面监听器
 */
public class MarketListener implements Listener {
    @EventHandler
    public void clickMarket(InventoryClickEvent event) {
        Market market = MMGlobalExchanges.market;
        if (!event.getInventory().getTitle().equals(market.getTitle())) {
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
                int id = Integer.parseInt(lore.get(lore.size() - 3).split(" ")[1]);
                int price = Integer.parseInt(lore.get(lore.size() - 1).split(" ")[1]);
                Map<Integer, Integer> good = new HashMap<>();
                good.put(id, price);
                MMGlobalExchanges.purchase.put(player.getName(), good);
                PurchaseConfirm purchaseConfirm = new PurchaseConfirm(MMGlobalExchanges.instance.getConfig().getString("market.confirm"));
                purchaseConfirm.open(player);
            }
            if (displayName == null) {return;}
            switch (slot) {
                case 45:
                    SelectItemListener.selectReturnInfo.put(player.getName(), "市场");
                    SelectItem selectItem = new SelectItem(MMGlobalExchanges.instance.getConfig().getString("market.select"));
                    selectItem.setInfo(player, " ");
                    selectItem.open(player);
                    break;
                case 46:
                    Search search = new Search(MMGlobalExchanges.instance.getConfig().getString("market.search"));
                    search.open(player);
                    break;
                case 47:
                    market.setPage(player, 1);
                    market.open(player);
                    break;
                case 48:
                    market.setPage(player, market.getPage(player) - 1);
                    market.open(player);
                    break;
                case 49:
                    PersonalInfo personalInfo = new PersonalInfo(MMGlobalExchanges.instance.getConfig().getString("market.personal.name"));
                    personalInfo.open(player);
                    break;
                case 50:
                    market.setPage(player, market.getPage(player) + 1);
                    market.open(player);
                    break;
                case 51:
                    market.setPage(player, (MMGlobalExchanges.marketFile.getSize(player) - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_max") + 1);
                    market.open(player);
                    break;
                case 53:
                    market.close(player);
                    break;
            }
        } else {
            event.setCancelled(true);
        }
    }
}
   