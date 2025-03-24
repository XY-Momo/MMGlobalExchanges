package xycm.momo.mmglobalexchanges.event.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.market.PersonalInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人出售记录界面监听器
 */
public class SellRecordListener implements Listener {
    // 出售记录界面返回哪个界面信息
    public static Map<String, String> sellReturnInfo = new HashMap<>();
    @EventHandler
    public void clickSellRecord(InventoryClickEvent event) {
        String title = MMGlobalExchanges.instance.getConfig().getString("market.personal.sell");
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
                        PersonalInfo personalInfo = new PersonalInfo(MMGlobalExchanges.instance.getConfig().getString("market.personal.name"));
                        personalInfo.open(player);
                    } else if (sellReturnInfo.get(player.getName()).equals("市场")) {
                        if (MMGlobalExchanges.market.getPage(player) == 0) {
                            MMGlobalExchanges.market.setPage(player, 1);
                            MMGlobalExchanges.market.open(player);
                        } else {
                            MMGlobalExchanges.market.open(player);
                        }
                    }
                }
                if (event.getRawSlot() == 47) {
                    MMGlobalExchanges.sellRecord.setPage(player, 1);
                    MMGlobalExchanges.sellRecord.open(player);
                }
                if (event.getRawSlot() == 48) {
                    MMGlobalExchanges.sellRecord.setPage(player, MMGlobalExchanges.sellRecord.getPage(player) - 1);
                    MMGlobalExchanges.sellRecord.open(player);
                }
                if (event.getRawSlot() == 50) {
                    MMGlobalExchanges.sellRecord.setPage(player, MMGlobalExchanges.sellRecord.getPage(player) + 1);
                    MMGlobalExchanges.sellRecord.open(player);
                }
                if (event.getRawSlot() == 51) {
                    MMGlobalExchanges.sellRecord.setPage(player, (MMGlobalExchanges.sellRecord.getSellNum() - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_personal_sell_max") + 1);
                    MMGlobalExchanges.sellRecord.open(player);
                }
            }
        }
    }
}
