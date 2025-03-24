package xycm.momo.mmglobalexchanges.event.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackLaunchItem;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackSelectItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索物品界面监听器
 */
public class BlackSelectItemListener implements Listener {

    // 上架界面返回哪个界面信息
    public static Map<String, String> selectReturnInfo = new HashMap<>();
    @EventHandler
    public void clickBlackSelectItem(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        BlackSelectItem selectItem = new BlackSelectItem(MMGlobalExchanges.instance.getConfig().getString("black_market.select"));
        BlackLaunchItem launchItem = new BlackLaunchItem(MMGlobalExchanges.instance.getConfig().getString("black_market.launch"), 0);
        // 检查是否是 selectItem 界面
        if (event.getView().getTitle().equals(selectItem.getTitle())) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }
            event.setCancelled(true);

            MMGlobalExchanges.launchItems.remove(player.getName());

            int rawSlot = event.getRawSlot();
            int inventorySize = event.getInventory().getSize();
            // 检查玩家是否从界面拿取物品
            if (rawSlot < inventorySize) {
                if (event.getRawSlot() == 46) {
                    if (selectReturnInfo.get(player.getName()).equals("市场")) {
                        MMGlobalExchanges.blackMarket.open(player);
                    } else if (selectReturnInfo.get(player.getName()).equals("上架记录")) {
                        MMGlobalExchanges.blackLaunchRecord.open(player);
                    }
                }
            } else {
                if (event.getCurrentItem().getType() != Material.AIR) {
                    if (MMGlobalExchanges.instance.getConfig().getString("black_match.mode").equals("name")) {
                        if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
                            List<String> black_name_list = MMGlobalExchanges.instance.getConfig().getStringList("black_name_list");
                            if (black_name_list != null && black_name_list.stream().anyMatch(s -> event.getCurrentItem().getItemMeta().getDisplayName().contains(s))) {
                                if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                                    selectItem.setInfo(player, "该物品不能上架！");
                                    selectItem.open(player);
                                } else {
                                    selectItem.open(player);
                                    player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "该物品不能上架！");
                                }
                            } else {
                                launchItem.addItem(0, event.getCurrentItem());
                                launchItem.setSlot(event.getRawSlot());
                                launchItem.open(player, 0);
                                MMGlobalExchanges.blackLaunchItems.put(player.getName(), launchItem);
                                event.setCurrentItem(new ItemStack(Material.AIR));
                            }
                        } else {
                            launchItem.addItem(0, event.getCurrentItem());
                            launchItem.setSlot(event.getRawSlot());
                            launchItem.open(player, 0);
                            MMGlobalExchanges.blackLaunchItems.put(player.getName(), launchItem);
                            event.setCurrentItem(new ItemStack(Material.AIR));
                        }
                    } else if (MMGlobalExchanges.instance.getConfig().getString("black_match.mode").equals("lore")) {
                        if (event.getCurrentItem().getItemMeta().hasLore()) {
                            List<String> black_lore_list = MMGlobalExchanges.instance.getConfig().getStringList("black_lore_list");
                            String lore = event.getCurrentItem().getItemMeta().getLore().get(MMGlobalExchanges.instance.getConfig().getInt("black_match.lore")).toLowerCase();
                            if (black_lore_list != null && black_lore_list.stream().anyMatch(lore::contains)) {
                                if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                                    selectItem.setInfo(player, "该物品不能上架！");
                                    selectItem.open(player);
                                } else {
                                    selectItem.open(player);
                                    player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "该物品不能上架！");
                                }
                            } else {
                                launchItem.addItem(0, event.getCurrentItem());
                                launchItem.setSlot(event.getRawSlot());
                                launchItem.open(player, 0);
                                MMGlobalExchanges.blackLaunchItems.put(player.getName(), launchItem);
                                event.setCurrentItem(new ItemStack(Material.AIR));
                            }
                        } else {
                            launchItem.addItem(0, event.getCurrentItem());
                            launchItem.setSlot(event.getRawSlot());
                            launchItem.open(player, 0);
                            MMGlobalExchanges.blackLaunchItems.put(player.getName(), launchItem);
                            event.setCurrentItem(new ItemStack(Material.AIR));
                        }
                    } else if (MMGlobalExchanges.instance.getConfig().getString("black_match.mode").equals("name-lore")) {
                        if (event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
                            List<String> black_name_list = MMGlobalExchanges.instance.getConfig().getStringList("black_name_list");
                            List<String> black_lore_list = MMGlobalExchanges.instance.getConfig().getStringList("black_lore_list");
                            if ((black_name_list != null && black_name_list.stream().anyMatch(s -> event.getCurrentItem().getItemMeta().getDisplayName().contains(s))) || (black_lore_list != null && black_lore_list.stream().anyMatch(lore -> event.getCurrentItem().getItemMeta().getLore().get(MMGlobalExchanges.instance.getConfig().getInt("black_match.lore")).toLowerCase().contains(lore)))) {
                                if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                                    selectItem.setInfo(player, "该物品不能上架！");
                                    selectItem.open(player);
                                } else {
                                    selectItem.open(player);
                                    player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "该物品不能上架！");
                                }
                            } else {
                                launchItem.addItem(0, event.getCurrentItem());
                                launchItem.setSlot(event.getRawSlot());
                                launchItem.open(player, 0);
                                MMGlobalExchanges.blackLaunchItems.put(player.getName(), launchItem);
                                event.setCurrentItem(new ItemStack(Material.AIR));
                            }
                        } else {
                            launchItem.addItem(0, event.getCurrentItem());
                            launchItem.setSlot(event.getRawSlot());
                            launchItem.open(player, 0);
                            MMGlobalExchanges.blackLaunchItems.put(player.getName(), launchItem);
                            event.setCurrentItem(new ItemStack(Material.AIR));
                        }
                    }
                } else {
                    player.sendMessage("");
                }
            }
        }
    }
}
