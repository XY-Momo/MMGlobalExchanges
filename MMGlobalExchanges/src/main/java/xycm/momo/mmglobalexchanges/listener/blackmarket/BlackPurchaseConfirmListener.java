package xycm.momo.mmglobalexchanges.listener.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackPurchaseConfirm;

/**
 * 购买确认界面监听器
 */
public class BlackPurchaseConfirmListener implements Listener {
    @EventHandler
    public void clickPurchaseConfirm(InventoryClickEvent event) {
        String title = MMGlobalExchanges.instance.getConfig().getString("black_market.confirm");
        if (event.getInventory().getTitle().equals(title)) {
            event.setCancelled(true);
            BlackPurchaseConfirm purchaseConfirm = new BlackPurchaseConfirm(title);
            Player player = (Player) event.getWhoClicked();

            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            int rawSlot = event.getRawSlot();
            int inventorySize = event.getInventory().getSize();

            ItemStack clickedItem = event.getCurrentItem();
            String displayName = clickedItem.getItemMeta().getDisplayName();
            if (displayName == null) {return;}
            if (rawSlot < inventorySize) {
                if (event.getRawSlot() == 0) {
                    purchaseConfirm.confirm(player);
                }
                if (event.getRawSlot() == 1) {
                    purchaseConfirm.returnMarket(player);
                }
            }
        }
    }
}
