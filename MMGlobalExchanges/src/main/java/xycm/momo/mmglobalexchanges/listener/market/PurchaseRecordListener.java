package xycm.momo.mmglobalexchanges.listener.market;

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
 * 个人购买记录界面监听器
 */
public class PurchaseRecordListener implements Listener {
    // 购买记录界面返回哪个界面信息
    public static Map<String, String> purchaseReturnInfo = new HashMap<>();
    @EventHandler
    public void clickPurchaseRecord(InventoryClickEvent event) {
        String title = MMGlobalExchanges.instance.getConfig().getString("market.personal.purchase");
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
                    if (purchaseReturnInfo.get(player.getName()).equals("个人信息")) {
                        PersonalInfo personalInfo = new PersonalInfo(MMGlobalExchanges.instance.getConfig().getString("market.personal.name"));
                        personalInfo.open(player);
                    } else if (purchaseReturnInfo.get(player.getName()).equals("市场")) {
                        if (MMGlobalExchanges.market.getPage(player) == 0) {
                            MMGlobalExchanges.market.setPage(player, 1);
                            MMGlobalExchanges.market.open(player);
                        } else {
                            MMGlobalExchanges.market.open(player);
                        }
                    }
                }
                if (event.getRawSlot() == 47) {
                    MMGlobalExchanges.purchaseRecord.setPage(player, 1);
                    MMGlobalExchanges.purchaseRecord.open(player);
                }
                if (event.getRawSlot() == 48) {
                    MMGlobalExchanges.purchaseRecord.setPage(player, MMGlobalExchanges.purchaseRecord.getPage(player) - 1);
                    MMGlobalExchanges.purchaseRecord.open(player);
                }
                if (event.getRawSlot() == 50) {
                    MMGlobalExchanges.purchaseRecord.setPage(player, MMGlobalExchanges.purchaseRecord.getPage(player) + 1);
                    MMGlobalExchanges.purchaseRecord.open(player);
                }
                if (event.getRawSlot() == 51) {
                    MMGlobalExchanges.purchaseRecord.setPage(player, (MMGlobalExchanges.purchaseRecord.getBuyNum() - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_personal_purchase_max") + 1);
                    MMGlobalExchanges.purchaseRecord.open(player);
                }
            }
        }
    }
}
