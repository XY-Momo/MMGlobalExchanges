package xycm.momo.mmglobalexchanges.ui.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.file.History;
import xycm.momo.mmglobalexchanges.file.PlayerData;
import xycm.momo.mmglobalexchanges.ui.Chest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人购买记录
 */
public class PurchaseRecord extends Chest {

    // 存储玩家上一次翻页记录
    private final Map<String, Integer> playerPages = new HashMap<>();
    private PlayerData playerData;

    public PurchaseRecord(String title) {
        super(title, 54);
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     * @param player 对应玩家
     */
    private void init(Player player) {
        this.playerData = new PlayerData(player.getName());
        int page = getPage(player);
        int max_item = MMGlobalExchanges.instance.getConfig().getInt("market_personal_purchase_max");
        this.addReturn();
        if (page == 1) {
            this.removeItem(47);
            this.removeItem(48);
            this.removeItem(50);
            this.removeItem(51);
            if (playerData.getBuyItems().size() > max_item) {
                this.addNextPage();
                this.addLastPage();
            }
        } else if (page < (playerData.getBuyItems().size() - 1) / max_item + 1) {
            this.addFirstPage();
            this.addPreviousPage();
            this.addNextPage();
            this.addLastPage();
        } else {
            this.addFirstPage();
            this.addPreviousPage();
            this.removeItem(50);
            this.removeItem(51);
        }
        addInfo(player);
    }

    /**
     * 获取玩家当前页码
     * @param player 对应玩家
     * @return 页码
     */
    public int getPage(Player player) {
        return playerPages.get(player.getName());
    }

    /**
     * 设置玩家当前页码
     * @param player 对应玩家
     * @param page 页码
     */
    public void setPage(Player player, int page) {
        playerPages.put(player.getName(), page);
    }

    /**
     * 打开个人购买记录界面
     * @param player 对应玩家
     */
    @Override
    public void open(Player player) {
        this.init(player);
        this.loadItems(player);
        super.open(player);
    }

    /**
     * 给购买记录界面添加商品
     * @param player 对应玩家
     */
    private void loadItems(Player player) {
        this.clearItems();
        int index = 0; // 计数器，模拟索引
        int page = getPage(player);
        History history = new History("Market");
        for (int id : playerData.getBuyItems()) {
            if (index / MMGlobalExchanges.instance.getConfig().getInt("market_personal_purchase_max") == page - 1) {
                int slot = index % MMGlobalExchanges.instance.getConfig().getInt("market_personal_purchase_max");
                ItemStack itemStack = history.getItem(id);
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
                String time = history.getSellTime(id);
                String detail = time.split(" ")[0];
                String brief = time.split(" ")[1].split("\\.")[0];
                lore.add("§f购买: " + detail);
                lore.add("§f时间: " + brief);
                lore.add("§f商品唯一ID: " + id);
                lore.add("§f卖家: " + history.getSeller(id));
                lore.add("§f价格: " + history.getPrice(id) + " 时息");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                addItem(slot, itemStack);
            }
            index++; // 增加计数器
        }
    }

    /**
     * 清除界面所有商品
     */
    private void clearItems() {
        for (int i = 0; i < 45; i++) {
            removeItem(i);
        }
    }

    /**
     * 获取购买记录数量
     * @return 购买记录数量
     */
    public int getBuyNum() {
        return playerData.getBuyItems().size();
    }

    /* 返回 */
    private void addReturn() {
        ItemStack item = new ItemStack(Material.BARRIER);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "返回键");
        item.setItemMeta(itemMeta);
        addItem(46, item);
    }

    /* 第一页 */
    private void addFirstPage() {
        ItemStack item = new ItemStack(Material.ARROW);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "第一页");
        item.setItemMeta(itemMeta);
        addItem(47, item);
    }

    /* 上一页 */
    private void addPreviousPage() {
        ItemStack item = new ItemStack(Material.ARROW);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "上一页");
        item.setItemMeta(itemMeta);
        addItem(48, item);
    }

    /* 下一页 */
    private void addNextPage() {
        ItemStack item = new ItemStack(Material.ARROW);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "下一页");
        item.setItemMeta(itemMeta);
        addItem(50, item);
    }

    /* 最后一页 */
    private void addLastPage() {
        ItemStack item = new ItemStack(Material.ARROW);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "最后一页");
        item.setItemMeta(itemMeta);
        addItem(51, item);
    }

    /* 提示信息 */
    private void addInfo(Player player) {
        ItemStack item = new ItemStack(Material.PAPER);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(" ");
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add(String.valueOf(getPage(player)));
        lore.add(String.valueOf((playerData.getBuyItems().size() - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_personal_purchase_max") + 1));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        addItem(52, item);
    }
}
