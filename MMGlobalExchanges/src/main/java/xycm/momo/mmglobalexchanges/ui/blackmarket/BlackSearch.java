package xycm.momo.mmglobalexchanges.ui.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.Chest;

import java.util.*;

/**
 * 搜索界面(可配合龙核界面)
 */
public class BlackSearch extends Chest {
    // 存储玩家上一次的翻页记录
    private final Map<String, Integer> playerPages = new HashMap<>();
    // 筛选过后的物品数量
    private int size;

    /**
     * 黑市搜索界面构造函数
     * @param title 设置界面标题
     */
    public BlackSearch(String title) {
        super(title, 54);
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     * @param player 对应玩家
     */
    private void init(Player player) {
        // 是否使用龙核
        if (!MMGlobalExchanges.instance.getConfig().getBoolean("use_dragoncore")) {
            this.loadItems(player);
            int page = getPage(player);
            if (page == 1) {
                this.removeItem(47);
                this.removeItem(48);
                this.removeItem(50);
                this.removeItem(51);
                if (this.size > MMGlobalExchanges.instance.getConfig().getInt("black_market_search_max")) {
                    this.addNextPage();
                    this.addLastPage();
                }
            } else if (page < (this.size - 1) / MMGlobalExchanges.instance.getConfig().getInt("black_market_search_max") + 1) {
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
        }
        this.addReturn();
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

    @Override
    public void open(Player player) {
        this.init(player);
        super.open(player);
    }

    private void loadItems(Player player) {
        List<ItemStack> items = MMGlobalExchanges.blackMarketFile.loadItems();
        Map<List<String>, Integer> itemStacks = new HashMap<>();
        for (ItemStack item : items) {
            List<String> s = new ArrayList<>();
            s.add(item.getType().toString());
            s.add(item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : "");
            itemStacks.put(s, itemStacks.getOrDefault(s, 0) + 1);
        }
        this.size = itemStacks.size();
        int index = 0;
        int page = getPage(player);
        for (Map.Entry<List<String>, Integer> entry : itemStacks.entrySet()) {
            List<String> m_n = entry.getKey();
            Material material = Material.getMaterial(m_n.get(0));
            String name = m_n.get(1);
            int amount = entry.getValue();
            // 一页多少物品
            if (index / MMGlobalExchanges.instance.getConfig().getInt("black_market_search_max") == page - 1) {
                int slot = index % MMGlobalExchanges.instance.getConfig().getInt("black_market_search_max");
                ItemStack itemStack = new ItemStack(material);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<String> lores = new ArrayList<>();
                lores.add("§f此类物品出现了 " + amount + " 次");
                if (!Objects.equals(name, "")) {
                    itemMeta.setDisplayName(name);
                }
                itemMeta.setLore(lores);
                itemStack.setItemMeta(itemMeta);
                addItem(slot, itemStack);
            }
            index++;
        }
    }

    /* 返回 */
    public void addReturn() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(MMGlobalExchanges.instance.getConfig().getString("black_market.search") + "返回");
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

}
