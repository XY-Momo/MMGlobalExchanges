package xycm.momo.mmglobalexchanges.listener.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.market.LaunchItem;
import xycm.momo.mmglobalexchanges.ui.market.SelectItem;

/**
 * 上架物品界面监听器
 */
public class LaunchItemListener implements Listener {
    @EventHandler
    public void clickLaunchItem(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        LaunchItem launchItem = MMGlobalExchanges.launchItems.get(player.getName());
        if (launchItem == null) {
            return;
        }
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }
        if (!event.getView().getTitle().equals(launchItem.getTitle())) {
            return;
        }
        event.setCancelled(true);

        int rawSlot = event.getRawSlot();
        int inventorySize = event.getInventory().getSize();

        SelectItem selectItem = new SelectItem(MMGlobalExchanges.instance.getConfig().getString("market.select"));

        // 检查玩家是否从 Market 界面拿取物品
        if (rawSlot < inventorySize) {
            switch (event.getRawSlot()) {
                case 0:
                    launchItem.returnItem(player);
                    break;
                case 1:
                    launchItem.addPriceNum(player, 0);
                    break;
                case 2:
                    launchItem.addPriceNum(player, 1);
                    break;
                case 3:
                    launchItem.addPriceNum(player, 2);
                    break;
                case 4:
                    launchItem.addPriceNum(player, 3);
                    break;
                case 5:
                    launchItem.addPriceNum(player, 4);
                    break;
                case 6:
                    launchItem.addPriceNum(player, 5);
                    break;
                case 7:
                    launchItem.addPriceNum(player, 6);
                    break;
                case 8:
                    launchItem.addPriceNum(player, 7);
                    break;
                case 9:
                    launchItem.addPriceNum(player, 8);
                    break;
                case 10:
                    launchItem.addPriceNum(player, 9);
                    break;
                case 11:
                    launchItem.open(player, 0);
                    break;
                case 12:
                    MMGlobalExchanges.marketFile.saveItem(player, launchItem.getPrice(), launchItem.getItem(0));
                    selectItem.open(player);
                    break;
                case 13:
                    launchItem.subPriceNum(player);
                    break;
                case 46:
                    launchItem.returnItem(player);
                    selectItem.setInfo(player, " ");
                    selectItem.open(player);
                    break;
            }
        } else {
            player.sendMessage("不能点！");
            event.setCancelled(true);
        }
    }
}
