package xycm.momo.mmglobalexchanges.listener.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackDelistConfirm;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackPersonalInfo;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackSelectItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人上架记录界面监听器
 */
public class BlackLaunchRecordListener implements Listener {
    // 上架记录界面返回哪个界面信息
    public static Map<String, String> launchReturnInfo = new HashMap<>();
    @EventHandler
    public void clickLaunchRecord(InventoryClickEvent event) {
        String title = MMGlobalExchanges.instance.getConfig().getString("black_market.personal.launch");
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }
        if (event.getView().getTitle().equals(title)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();

            int rawSlot = event.getRawSlot();
            if (rawSlot < 45) {
                ItemStack item = event.getCurrentItem();
                List<String> lore = item.getItemMeta().getLore();
                int id = Integer.parseInt(lore.get(lore.size() - 2).split(" ")[1]);
                MMGlobalExchanges.blackDelist.put(player.getName(), id);
                BlackDelistConfirm delistConfirm = new BlackDelistConfirm(MMGlobalExchanges.instance.getConfig().getString("black_market.personal.confirm"));
                delistConfirm.open(player);
            }
            int inventorySize = event.getInventory().getSize();
            if (event.getCurrentItem().getType() == Material.AIR) {return;}
            // 检查玩家是否从界面拿取物品
            if (rawSlot < inventorySize) {
                if (event.getRawSlot() == 45) {
                    BlackSelectItemListener.selectReturnInfo.put(player.getName(), "上架记录");
                    BlackSelectItem selectItem = new BlackSelectItem(MMGlobalExchanges.instance.getConfig().getString("black_market.select"));
                    selectItem.setInfo(player, " ");
                    selectItem.open(player);
                }
                if (event.getRawSlot() == 46) {
                    if (launchReturnInfo.get(player.getName()).equals("个人信息")) {
                        BlackPersonalInfo personalInfo = new BlackPersonalInfo(MMGlobalExchanges.instance.getConfig().getString("black_market.personal.name"));
                        personalInfo.open(player);
                    } else if (launchReturnInfo.get(player.getName()).equals("黑市")) {
                        if (MMGlobalExchanges.blackMarket.getPage(player) == 0) {
                            MMGlobalExchanges.blackMarket.setPage(player, 1);
                            MMGlobalExchanges.blackMarket.open(player);
                        } else {
                            MMGlobalExchanges.blackMarket.open(player);
                        }
                    }
                }
                if (event.getRawSlot() == 47) {
                    MMGlobalExchanges.blackLaunchRecord.setPage(player, 1);
                    MMGlobalExchanges.blackLaunchRecord.open(player);
                }
                if (event.getRawSlot() == 48) {
                    MMGlobalExchanges.blackLaunchRecord.setPage(player, MMGlobalExchanges.blackLaunchRecord.getPage(player) - 1);
                    MMGlobalExchanges.blackLaunchRecord.open(player);
                }
                if (event.getRawSlot() == 50) {
                    MMGlobalExchanges.blackLaunchRecord.setPage(player, MMGlobalExchanges.blackLaunchRecord.getPage(player) + 1);
                    MMGlobalExchanges.blackLaunchRecord.open(player);
                }
                if (event.getRawSlot() == 51) {
                    MMGlobalExchanges.blackLaunchRecord.setPage(player, (MMGlobalExchanges.blackLaunchRecord.getLaunchNum() - 1) / MMGlobalExchanges.instance.getConfig().getInt("black_market_personal_launch_max") + 1);
                    MMGlobalExchanges.blackLaunchRecord.open(player);
                }
            }
        }
    }
}
