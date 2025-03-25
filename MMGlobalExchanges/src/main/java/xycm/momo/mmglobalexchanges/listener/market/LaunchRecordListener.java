package xycm.momo.mmglobalexchanges.listener.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.market.DelistConfirm;
import xycm.momo.mmglobalexchanges.ui.market.PersonalInfo;
import xycm.momo.mmglobalexchanges.ui.market.SelectItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人上架记录界面监听器
 */
public class LaunchRecordListener implements Listener {
    // 上架记录界面返回哪个界面信息
    public static Map<String, String> launchReturnInfo = new HashMap<>();
    @EventHandler
    public void clickLaunchRecord(InventoryClickEvent event) {
        String title = MMGlobalExchanges.instance.getConfig().getString("market.personal.launch");
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
                MMGlobalExchanges.delist.put(player.getName(), id);
                DelistConfirm delistConfirm = new DelistConfirm(MMGlobalExchanges.instance.getConfig().getString("market.personal.confirm"));
                delistConfirm.open(player);
            }
            int inventorySize = event.getInventory().getSize();
            if (event.getCurrentItem().getType() == Material.AIR) {return;}
            // 检查玩家是否从界面拿取物品
            if (rawSlot < inventorySize) {
                if (event.getRawSlot() == 45) {
                    SelectItemListener.selectReturnInfo.put(player.getName(), "上架记录");
                    SelectItem selectItem = new SelectItem(MMGlobalExchanges.instance.getConfig().getString("market.select"));
                    selectItem.setInfo(player, " ");
                    selectItem.open(player);
                }
                if (event.getRawSlot() == 46) {
                    if (launchReturnInfo.get(player.getName()).equals("个人信息")) {
                        PersonalInfo personalInfo = new PersonalInfo(MMGlobalExchanges.instance.getConfig().getString("market.personal.name"));
                        personalInfo.open(player);
                    } else if (launchReturnInfo.get(player.getName()).equals("市场")) {
                        if (MMGlobalExchanges.market.getPage(player) == 0) {
                            MMGlobalExchanges.market.setPage(player, 1);
                            MMGlobalExchanges.market.open(player);
                        } else {
                            MMGlobalExchanges.market.open(player);
                        }
                    }
                }
                if (event.getRawSlot() == 47) {
                    MMGlobalExchanges.launchRecord.setPage(player, 1);
                    MMGlobalExchanges.launchRecord.open(player);
                }
                if (event.getRawSlot() == 48) {
                    MMGlobalExchanges.launchRecord.setPage(player, MMGlobalExchanges.launchRecord.getPage(player) - 1);
                    MMGlobalExchanges.launchRecord.open(player);
                }
                if (event.getRawSlot() == 50) {
                    MMGlobalExchanges.launchRecord.setPage(player, MMGlobalExchanges.launchRecord.getPage(player) + 1);
                    MMGlobalExchanges.launchRecord.open(player);
                }
                if (event.getRawSlot() == 51) {
                    MMGlobalExchanges.launchRecord.setPage(player, (MMGlobalExchanges.launchRecord.getLaunchNum() - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_personal_launch_max") + 1);
                    MMGlobalExchanges.launchRecord.open(player);
                }
            }
        }
    }
}
