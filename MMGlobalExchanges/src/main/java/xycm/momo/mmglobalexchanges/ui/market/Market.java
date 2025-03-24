package xycm.momo.mmglobalexchanges.ui.market;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.file.MarketFile;
import xycm.momo.mmglobalexchanges.file.PlayerData;
import xycm.momo.mmglobalexchanges.ui.Chest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 市场界面
 */
public class Market extends Chest {

    // 存储玩家上一次的翻页记录
    private final Map<String, Integer> playerPages = new HashMap<>();
    // 存储玩家返回界面的提示信息
    private final Map<String, String> playerInfo = new HashMap<>();
    // 存储玩家搜索的过滤词
    private final Map<String, String> playerFilter = new HashMap<>();
    // 存储玩家打开过的市场界面
    private final Map<String, Market> playerInventory = new HashMap<>();

    /**
     * 市场界面构造函数
     * @param title 设置界面标题
     */
    public Market(String title) {
        super(title, 54);
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     * @param player 对应玩家
     */
    private void init(Player player) {
        this.addLaunch();
        this.addSearch();
        int page = getPage(player);
        if (page == 1) {
            this.removeItem(47);
            this.removeItem(48);
            this.removeItem(50);
            this.removeItem(51);
            if (MMGlobalExchanges.marketFile.getSize(player) > MMGlobalExchanges.instance.getConfig().getInt("market_max")) {
                this.addNextPage();
                this.addLastPage();
            }
        } else if (page < (MMGlobalExchanges.marketFile.getSize(player) - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_max") + 1) {
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
        this.addPersonalInfo(player);
        this.addInfo(player);
        this.addCloseButton();
    }

    /**
     * 获取玩家当前页码
     * @param player 对应玩家
     * @return 页码
     */
    public int getPage(Player player) {
        if (playerPages.get(player.getName()) == null) return 0;
        else return playerPages.get(player.getName());
    }

    /**
     * 设置玩家当前页码
     * @param player 对应玩家
     * @param page 设置页码
     */
    public void setPage(Player player, int page) {
        playerPages.put(player.getName(), page);
    }

    /**
     * 获取玩家返回界面的提示信息
     * @param player 对应玩家
     * @return 提示信息
     */
    public String getInfo(Player player) {
        return playerInfo.get(player.getName());
    }

    /**
     * 设置玩家返回界面的提示信息
     * @param player 对应玩家
     * @param info 提示信息
     */
    public void setInfo(Player player, String info) { playerInfo.put(player.getName(), info); }

    /**
     * 获取玩家搜索的过滤词
     * @param player 对应玩家
     * @return 过滤词
     */
    public String getFilter(Player player) {
        return playerFilter.get(player.getName());
    }

    /**
     * 设置玩家搜索的过滤词
     * @param player 对应玩家
     * @param filter 过滤词
     */
    public void setFilter(Player player, String filter) { playerFilter.put(player.getName(), filter);}

    /**
     * 打开市场界面
     * @param player 对应玩家
     */
    @Override
    public void open(Player player) {
        this.init(player);
        this.loadItems(player);
        super.open(player);
    }

    /**
     * 给市场界面添加商品
     * @param player 对应玩家
     */
    private void loadItems(Player player) {
        Map<Integer, Map<Integer, ItemStack>> items = MMGlobalExchanges.marketFile.loadItems(playerFilter.get(player.getName()));
        this.clearItems();
        int index = 0;
        int page = getPage(player);
        for (Map.Entry<Integer, Map<Integer, ItemStack>> entry : items.entrySet()) {
            // 一页多少物品
            if (index / MMGlobalExchanges.instance.getConfig().getInt("market_max") == page - 1) {
                int id = entry.getKey();
                int slot = index % MMGlobalExchanges.instance.getConfig().getInt("market_max");
                int price;
                ItemStack itemStack = new ItemStack(Material.BARRIER);
                Map<Integer, ItemStack> p_item = entry.getValue();
                for (Map.Entry<Integer, ItemStack> item : p_item.entrySet()) {
                    price = item.getKey();
                    itemStack = item.getValue();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
                    lore.add("§f商品唯一ID: " + id);
                    lore.add("§f卖家: " + MMGlobalExchanges.marketFile.getSolder(id));
                    lore.add("§f价格: " + price + " 时息");
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                }
                addItem(slot, itemStack);
            }
            index++;
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

    /* 上架 */
    private void addLaunch() {
        ItemStack item = new ItemStack(Material.ANVIL);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "上架");
        item.setItemMeta(itemMeta);
        addItem(45, item);
    }

    /* 搜索 */
    private void addSearch() {
        ItemStack item = new ItemStack(Material.COMPASS);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "搜索");
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

    /* 个人信息 */
    private void addPersonalInfo(Player player) {
        PlayerData playerData = new PlayerData(player.getName());
        ItemStack item = new ItemStack(Material.APPLE);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "个人信息");
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add("§f用户: " + player.getName());
        lore.add("§f购买次数: " + playerData.getBuyNumber() + " 次");
        lore.add("§f购买总价: " + playerData.getBuyPrice() + " 时息");
        lore.add("§f出售次数: " + playerData.getSellNumber() + " 次");
        lore.add("§f出售总价: " + playerData.getSellPrice() + " 时息");
        lore.add("§f正在上架: " + playerData.getLaunchItems().size() + " 件商品");
        lore.add("§f邮件: " + playerData.getMailItems().size() + " 件商品");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        addItem(49, item);
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
        itemMeta.setDisplayName(getInfo(player) == null ? " " : getInfo(player));
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add(String.valueOf(getPage(player)));
        lore.add(String.valueOf((MMGlobalExchanges.marketFile.getSize(player) - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_max") + 1));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        addItem(52, item);
    }

    /* 关闭按钮 */
    private void addCloseButton() {
        ItemStack item = new ItemStack(Material.BARRIER);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "关闭");
        item.setItemMeta(itemMeta);
        addItem(53, item);
    }

    /**
     * 在界面物品发生变化时，若有其他玩家处于该界面则刷新界面
     * @param id 发生变化的商品ID
     */
    public static void freshMarket(int id) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getWorld().getName();
            if (MMGlobalExchanges.market.getPage(player) == 0) {
                continue;
            }
            if (MMGlobalExchanges.market.getPage(player) < MMGlobalExchanges.marketFile.getItemPage(id)) {
                continue;
            }
            if (player.getOpenInventory().getTitle().equals(MMGlobalExchanges.instance.getConfig().getString("market.name"))) {
                MMGlobalExchanges.market.open(player);
            }
        }
    }
}
