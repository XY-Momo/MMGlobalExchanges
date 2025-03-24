package xycm.momo.mmglobalexchanges.event.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.market.DelistConfirm;

/**
 * 删除上架商品确认界面监听器
 */
public class DelistConfirmListener implements Listener {
    @EventHandler
    public void clickDelistConfirm(InventoryClickEvent event) {
        String title = MMGlobalExchanges.instance.getConfig().getString("market.personal.confirm");
        if (event.getInventory().getTitle().equals(title)) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            DelistConfirm delistConfirm = new DelistConfirm(title);
            Player player = (Player) event.getWhoClicked();

            int rawSlot = event.getRawSlot();
            int inventorySize = event.getInventory().getSize();
            // 检查玩家是否从 Market 界面拿取物品
            ItemStack clickedItem = event.getCurrentItem();
            String displayName = clickedItem.getItemMeta().getDisplayName();
            if (displayName == null) {return;}
            if (rawSlot < inventorySize) {
                if (event.getRawSlot() == 0) {
                    delistConfirm.confirm(player);
                }
                if (event.getRawSlot() == 1) {
                    delistConfirm.returnLaunchRecord(player);
                }
            }
        }
    }
}
