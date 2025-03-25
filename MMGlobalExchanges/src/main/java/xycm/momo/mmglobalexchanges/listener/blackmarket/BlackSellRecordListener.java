package xycm.momo.mmglobalexchanges.listener.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackPersonalInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人出售记录界面监听器
 */
public class BlackSellRecordListener implements Listener {
    // 出售记录界面返回哪个界面信息
    public static Map<String, String> sellReturnInfo = new HashMap<>();
    @EventHandler
    public void clickSellRecord(InventoryClickEvent event) {
        String title = MMGlobalExchanges.instance.getConfig().getString("black_market.personal.sell");
        if (event.getView().getTitle().equals(title)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            int rawSlot = event.getRawSlot();
            int inventorySize = event.getInventory().getSize();
            // 检查玩家是否从界面拿取物品
            if (rawSlot < inventorySize) {
                if (event.getRawSlot() == 46) {
                    if (sellReturnInfo.get(player.getName()).equals("个人信息")) {
                        BlackPersonalInfo personalInfo = new BlackPersonalInfo(MMGlobalExchanges.instance.getConfig().getString("black_market.personal.name"));
                        personalInfo.open(player);
                    } else if (sellReturnInfo.get(player.getName()).equals("黑市")) {
                        if (MMGlobalExchanges.blackMarket.getPage(player) == 0) {
                            MMGlobalExchanges.blackMarket.setPage(player, 1);
                            MMGlobalExchanges.blackMarket.open(player);
                        } else {
                            MMGlobalExchanges.blackMarket.open(player);
                        }
                    }
                }
                if (event.getRawSlot() == 47) {
                    MMGlobalExchanges.blackSellRecord.setPage(player, 1);
                    MMGlobalExchanges.blackSellRecord.open(player);
                }
                if (event.getRawSlot() == 48) {
                    MMGlobalExchanges.blackSellRecord.setPage(player, MMGlobalExchanges.blackSellRecord.getPage(player) - 1);
                    MMGlobalExchanges.blackSellRecord.open(player);
                }
                if (event.getRawSlot() == 50) {
                    MMGlobalExchanges.blackSellRecord.setPage(player, MMGlobalExchanges.blackSellRecord.getPage(player) + 1);
                    MMGlobalExchanges.blackSellRecord.open(player);
                }
                if (event.getRawSlot() == 51) {
                    MMGlobalExchanges.blackSellRecord.setPage(player, (MMGlobalExchanges.blackSellRecord.getSellNum() - 1) / MMGlobalExchanges.instance.getConfig().getInt("black_market_personal_sell_max") + 1);
                    MMGlobalExchanges.blackSellRecord.open(player);
                }
            }
        }
    }
}
