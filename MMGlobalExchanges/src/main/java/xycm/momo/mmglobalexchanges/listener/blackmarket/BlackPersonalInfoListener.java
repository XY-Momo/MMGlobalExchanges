package xycm.momo.mmglobalexchanges.listener.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackPersonalInfo;

/**
 * 个人信息界面监听器
 */
public class BlackPersonalInfoListener implements Listener {
    @EventHandler
    public void clickPersonalInfo(InventoryClickEvent event) {
        BlackPersonalInfo personalInfo = new BlackPersonalInfo(MMGlobalExchanges.instance.getConfig().getString("black_market.personal.name"));
        if (event.getView().getTitle().equals(personalInfo.getTitle())) {
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
                if (event.getRawSlot() == 0) {
                    BlackPurchaseRecordListener.purchaseReturnInfo.put(player.getName(), "个人信息");
                    MMGlobalExchanges.blackPurchaseRecord.setPage(player, 1);
                    MMGlobalExchanges.blackPurchaseRecord.open(player);
                }
                if (event.getRawSlot() == 1) {
                    BlackSellRecordListener.sellReturnInfo.put(player.getName(), "个人信息");
                    MMGlobalExchanges.blackSellRecord.setPage(player, 1);
                    MMGlobalExchanges.blackSellRecord.open(player);
                }
                if (event.getRawSlot() == 4) {
                    BlackLaunchRecordListener.launchReturnInfo.put(player.getName(), "个人信息");
                    MMGlobalExchanges.blackLaunchRecord.setPage(player, 1);
                    MMGlobalExchanges.blackLaunchRecord.open(player);
                }
                if (event.getRawSlot() == 5) {
                    BlackMailListener.mailReturnInfo.put(player.getName(), "个人信息");
                    MMGlobalExchanges.blackMail.setPage(player, 1);
                    MMGlobalExchanges.blackMail.open(player);
                }
                if (event.getRawSlot() == 46) {
                    MMGlobalExchanges.blackMarket.open(player);
                }
            }
        }
    }
}
