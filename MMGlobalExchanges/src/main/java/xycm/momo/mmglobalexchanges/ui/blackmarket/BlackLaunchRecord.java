package xycm.momo.mmglobalexchanges.ui.blackmarket;

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
 * 个人上架记录
 */
public class BlackLaunchRecord extends Chest {

    // 存储玩家上一次翻页记录
    private final Map<String, Integer> playerPages = new HashMap<>();
    private PlayerData playerData;
    // 存储玩家返回界面的提示信息
    private final Map<String, String> playerInfo = new HashMap<>();
    public BlackLaunchRecord(String title) {
        super(title, 54);
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     * @param player 对应玩家
     */
    private void init(Player player) {
        this.playerData = new PlayerData(player.getName());
        int page = getPage(player);
        int max_item = MMGlobalExchanges.instance.getConfig().getInt("black_market_personal_launch_max");
        this.addLaunch();
        this.addReturn();
        if (page == 1) {
            this.removeItem(47);
            this.removeItem(48);
            this.removeItem(50);
            this.removeItem(51);
            if (playerData.getBlackLaunchItems().size() > max_item) {
                this.addNextPage();
                this.addLastPage();
            }
        } else if (page < (playerData.getBlackLaunchItems().size() - 1) / max_item + 1) {
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
     * 打开个人上架记录
     * @param player 对应玩家
     */
    @Override
    public void open(Player player) {
        this.init(player);
        this.loadItems(player);
        super.open(player);
    }

    /**
     * 给个人上架记录添加商品
     * @param player 对应玩家
     */
    private void loadItems(Player player) {
        this.clearItems();
        int index = 0; // 计数器，模拟索引
        int page = getPage(player);
        History history = new History("BlackMarket");
        for (int id : playerData.getBlackLaunchItems()) {
            if (index / MMGlobalExchanges.instance.getConfig().getInt("black_market_personal_launch_max") == page - 1) {
                int slot = index % MMGlobalExchanges.instance.getConfig().getInt("black_market_personal_launch_max");
                ItemStack itemStack = history.getItem(id);
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
                String time = history.getLaunchTime(id);
                String detail = time.split(" ")[0];
                String brief = time.split(" ")[1].split("\\.")[0];
                lore.add("§f上架: " + detail);
                lore.add("§f时间: " + brief);
                lore.add("§f商品唯一ID: " + id);
                lore.add("§f价格: " + history.getPrice(id) + " 时息");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                addItem(slot, itemStack);
            }
            index++; // 增加计数器
        }
    }

    /**
     * 清空个人上架记录商品
     */
    private void clearItems() {
        for (int i = 0; i < 45; i++) {
            removeItem(i);
        }
    }

    /**
     * 获取个人上架记录商品数量
     * @return 商品数量
     */
    public int getLaunchNum() {
        return playerData.getBlackLaunchItems().size();
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
        itemMeta.setDisplayName(getInfo(player) == null ? " " : getInfo(player));
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add(String.valueOf(getPage(player)));
        lore.add(String.valueOf((playerData.getBlackLaunchItems().size() - 1) / MMGlobalExchanges.instance.getConfig().getInt("black_market_personal_launch_max") + 1));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        addItem(52, item);
    }
}
