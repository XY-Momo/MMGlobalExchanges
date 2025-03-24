package xycm.momo.mmglobalexchanges.event.market;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.file.PlayerData;
import xycm.momo.mmglobalexchanges.thread.MailInfo;
import xycm.momo.mmglobalexchanges.ui.market.PersonalInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人邮箱界面监听器
 */
public class MailListener implements Listener {
    // 邮件界面返回哪个界面信息
    public static Map<String, String> mailReturnInfo = new HashMap<>();
    @EventHandler
    public void clickMail(InventoryClickEvent event) {
        String title = MMGlobalExchanges.instance.getConfig().getString("market.personal.mail");
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }
        if (event.getView().getTitle().equals(title)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();

            int rawSlot = event.getRawSlot();
            PlayerData playerData = new PlayerData(player.getName());
            if (rawSlot < 45) {
                ItemStack item = event.getCurrentItem();
                List<String> lore = item.getItemMeta().getLore();
                int id = Integer.parseInt(lore.get(lore.size() - 3).split(" ")[1]);
                int price = Integer.parseInt(lore.get(lore.size() - 1).split(" ")[1]);
                playerData.removeMailItem(id);
                playerData.addSellItem(id);
                playerData.addSellNumber();
                playerData.addSellPrice(price);

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MMGlobalExchanges.instance.getConfig().getString("add_money").replace("<player>", player.getName()).replace("<price>", String.valueOf(price)));
                if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                    MMGlobalExchanges.mail.setInfo(player, "收入 " + price + " 时息");
                    MMGlobalExchanges.mail.open(player);
                    MailInfo.freshMailInfo(player);
                } else {
                    MMGlobalExchanges.mail.open(player);
                    player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "收入 " + price + " 时息");
                }
            }
            int inventorySize = event.getInventory().getSize();
            // 检查玩家是否从界面拿取物品
            if (rawSlot < inventorySize) {
                if (event.getRawSlot() == 46) {
                    if (mailReturnInfo.get(player.getName()).equals("个人信息")) {
                        PersonalInfo personalInfo = new PersonalInfo(MMGlobalExchanges.instance.getConfig().getString("market.personal.name"));
                        personalInfo.open(player);
                    } else if (mailReturnInfo.get(player.getName()).equals("市场")) {
                        if (MMGlobalExchanges.market.getPage(player) == 0) {
                            MMGlobalExchanges.market.setPage(player, 1);
                            MMGlobalExchanges.market.open(player);
                        } else {
                            MMGlobalExchanges.market.open(player);
                        }
                    }
                }
                if (event.getRawSlot() == 47) {
                    MMGlobalExchanges.mail.setPage(player, 1);
                    MMGlobalExchanges.mail.open(player);
                }
                if (event.getRawSlot() == 48) {
                    MMGlobalExchanges.mail.setPage(player, MMGlobalExchanges.mail.getPage(player) - 1);
                    MMGlobalExchanges.mail.open(player);
                }
                if (event.getRawSlot() == 50) {
                    MMGlobalExchanges.mail.setPage(player, MMGlobalExchanges.mail.getPage(player) + 1);
                    MMGlobalExchanges.mail.open(player);
                }
                if (event.getRawSlot() == 51) {
                    MMGlobalExchanges.mail.setPage(player, (MMGlobalExchanges.mail.getMailNum() - 1) / MMGlobalExchanges.instance.getConfig().getInt("black_market_personal_mail_max") + 1);
                    MMGlobalExchanges.mail.open(player);
                }
                if (event.getRawSlot() == 53) {
                    MMGlobalExchanges.mail.pickAll(player);
                }
            }
        }
    }
}
