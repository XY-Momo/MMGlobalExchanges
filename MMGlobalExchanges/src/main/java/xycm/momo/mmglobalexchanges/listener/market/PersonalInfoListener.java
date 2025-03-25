package xycm.momo.mmglobalexchanges.listener.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.market.PersonalInfo;

/**
 * 个人信息界面监听器
 */
public class PersonalInfoListener implements Listener {
    @EventHandler
    public void clickPersonalInfo(InventoryClickEvent event) {
        PersonalInfo personalInfo = new PersonalInfo(MMGlobalExchanges.instance.getConfig().getString("market.personal.name"));
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
                    PurchaseRecordListener.purchaseReturnInfo.put(player.getName(), "个人信息");
                    MMGlobalExchanges.purchaseRecord.setPage(player, 1);
                    MMGlobalExchanges.purchaseRecord.open(player);
                }
                if (event.getRawSlot() == 1) {
                    SellRecordListener.sellReturnInfo.put(player.getName(), "个人信息");
                    MMGlobalExchanges.sellRecord.setPage(player, 1);
                    MMGlobalExchanges.sellRecord.open(player);
                }
                if (event.getRawSlot() == 4) {
                    LaunchRecordListener.launchReturnInfo.put(player.getName(), "个人信息");
                    MMGlobalExchanges.launchRecord.setPage(player, 1);
                    MMGlobalExchanges.launchRecord.open(player);
                }
                if (event.getRawSlot() == 5) {
                    MailListener.mailReturnInfo.put(player.getName(), "个人信息");
                    MMGlobalExchanges.mail.setPage(player, 1);
                    MMGlobalExchanges.mail.open(player);
                }
                if (event.getRawSlot() == 46) {
                    MMGlobalExchanges.market.open(player);
                }
            }
        }
    }
}
