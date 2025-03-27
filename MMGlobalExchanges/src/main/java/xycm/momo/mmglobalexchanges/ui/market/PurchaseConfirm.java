package xycm.momo.mmglobalexchanges.ui.market;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.file.History;
import xycm.momo.mmglobalexchanges.file.PlayerData;
import xycm.momo.mmglobalexchanges.thread.MarketInfo;
import xycm.momo.mmglobalexchanges.ui.Chest;

import java.util.Map;

/**
 * 购买确认界面
 */
public class PurchaseConfirm extends Chest {

    private final History history;

    /**
     * 购买确认构造函数
     * @param title 标题
     */
    public PurchaseConfirm(String title) {
        super(title, 9);
        this.history = new History("Market");
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     */
    private void init() {
        addConfirm();
        addReturn();
    }

    /**
     * 打开购买确认界面
     * @param player 对应玩家
     */
    @Override
    public void open(Player player) {
        init();
        super.open(player);
    }

    /**
     * 确认购买
     * @param player 对应玩家
     */
    public void confirm(Player player) {
        Map<Integer, Integer> good = MMGlobalExchanges.purchase.get(player.getName());
        for (Map.Entry<Integer, Integer> entry : good.entrySet()) {
            int id = entry.getKey();
            int price = entry.getValue();
            if (MMGlobalExchanges.marketFile.getItem(id) == null) {
                if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                    MMGlobalExchanges.market.setInfo(player, "交易失败，商品已被购买或下架");
                    returnMarket(player);
                    MarketInfo.freshMarketInfo(player);
                } else {
                    returnMarket(player);
                    player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "交易失败，商品已被购买或下架");
                }
            } else {
                float money = Float.parseFloat(PlaceholderAPI.setPlaceholders((OfflinePlayer) player, MMGlobalExchanges.instance.getConfig().getString("economy_PAPI")));
                if (money < price) {
                    if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                        MMGlobalExchanges.market.setInfo(player, "交易失败，时息不够");
                        returnMarket(player);
                        MarketInfo.freshMarketInfo(player);
                    } else {
                        returnMarket(player);
                        player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "交易失败，时息不够");
                    }
                } else {
                    for (int i = 0; i < 36; i++) {
                        if (player.getInventory().getItem(i) == null) {
                            player.getInventory().setItem(i, MMGlobalExchanges.marketFile.getItem(id));
                            break;
                        }
                    }
                    history.setSellTime(id);
                    history.setBuyer(id, player);
                    history.changeLaunching(id, false);
                    MMGlobalExchanges.marketFile.removeItem(id);

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MMGlobalExchanges.instance.getConfig().getString("sub_money").replace("<player>", player.getName()).replace("<price>", String.valueOf(price)));

                    // ！刷新需要再玩家回界面之前！
                    Market.freshMarket(id);
                    if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                        MMGlobalExchanges.market.setInfo(player, "交易成功");
                        returnMarket(player);
                        MarketInfo.freshMarketInfo(player);
                    } else {
                        returnMarket(player);
                        player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "交易成功");
                    }

                    PlayerData buyer = new PlayerData(player.getName());
                    buyer.addBuyNumber();
                    buyer.addBuyPrice(price);
                    buyer.addBuyItem(id);

                    PlayerData seller = new PlayerData(history.getSeller(id));
                    seller.addMailItem(id);
                    seller.removeLaunchItem(id);
                }
            }
        }
        MMGlobalExchanges.purchase.remove(player.getName());
    }

    /**
     * 返回市场界面
     * @param player 对应玩家
     */
    public void returnMarket(Player player) {
        MMGlobalExchanges.market.open(player);
    }

    /* 确定键 */
    private void addConfirm() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getTitle() + "确定键");
        item.setItemMeta(meta);
        this.addItem(0, item);
    }

    /* 返回键 */
    private void addReturn() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getTitle() + "返回键");
        item.setItemMeta(meta);
        this.addItem(1, item);
    }

}
