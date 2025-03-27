package xycm.momo.mmglobalexchanges.ui.blackmarket;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.file.PlayerData;
import xycm.momo.mmglobalexchanges.ui.Chest;

import java.util.*;

/**
 * 黑市界面
 */
public class BlackMarket extends Chest {

    // 存储玩家上一次的翻页记录
    private final Map<String, Integer> playerPages = new HashMap<>();
    // 存储玩家返回界面的提示信息
    private final Map<String, String> playerInfo = new HashMap<>();
    // 存储玩家搜索的过滤词
    private final Map<String, String> playerFilter = new HashMap<>();

    private String hid = MMGlobalExchanges.instance.getConfig().getString("black_market_hidden_char");

    /**
     * 黑市界面构造函数
     * @param title 设置界面标题
     */
    public BlackMarket(String title) {
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
            if (MMGlobalExchanges.blackMarketFile.getSize(player) > MMGlobalExchanges.instance.getConfig().getInt("black_market_max")) {
                this.addNextPage();
                this.addLastPage();
            }
        } else if (page < (MMGlobalExchanges.blackMarketFile.getSize(player) - 1) / MMGlobalExchanges.instance.getConfig().getInt("black_market_max") + 1) {
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
     * 设置隐藏信息后显示的字符
     * @param hid 字符
     */
    public void setHid(String hid) {
        this.hid = hid;
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
     * 打开黑市界面
     * @param player 对应玩家
     */
    @Override
    public void open(Player player) {
        this.init(player);
        this.loadItems(player);
        super.open(player);
    }

    /**
     * 给黑市界面添加商品
     * @param player 对应玩家
     */
    private void loadItems(Player player) {
        Map<Integer, Map<Integer, ItemStack>> items = MMGlobalExchanges.blackMarketFile.loadItems(playerFilter.get(player.getName()));
        Random r = new Random();
        this.clearItems();
        int index = 0;
        int page = getPage(player);
        for (Map.Entry<Integer, Map<Integer, ItemStack>> entry : items.entrySet()) {
            // 一页多少物品
            if (index / MMGlobalExchanges.instance.getConfig().getInt("black_market_max") == page - 1) {
                int id = entry.getKey();
                int slot = index % MMGlobalExchanges.instance.getConfig().getInt("black_market_max");
                int price;
                ItemStack itemStack = new ItemStack(Material.BARRIER);
                Map<Integer, ItemStack> p_item = entry.getValue();
                for (Map.Entry<Integer, ItemStack> item : p_item.entrySet()) {
                    price = item.getKey();
                    itemStack = item.getValue();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
                    // 将lore索引指定的lore信息模糊
                    List<Integer> hid_list = MMGlobalExchanges.instance.getConfig().getIntegerList("black_market_hidden_index");
                    Set<Integer> hid_set = new TreeSet<>(hid_list);
                    int first_hid = hid_set.stream().findFirst().get();
                    // 所写需掩盖的第一个词条是否存在
                    if (lore.size() > first_hid) {
                        for (int i : hid_set) {
                            // 过滤超过索引的词条
                            if (lore.size() > i) {
                                String l = lore.get(i);
                                // 是否需要分隔符
                                if (MMGlobalExchanges.instance.getConfig().getString("black_market_hidden.split") != null) {
                                    List<String> splits = MMGlobalExchanges.instance.getConfig().getStringList("black_market_hidden.split");
                                    String[] split = new String[1];
                                    String regex = "";
                                    for (String s : splits) {
                                        if (l.split(s).length > 1) {
                                            split = l.split(s);
                                            regex = s;
                                            break;
                                        } else {
                                            split[0] = l;
                                        }
                                    }
                                    // 是否有写需要分隔后提取的索引
                                    if (MMGlobalExchanges.instance.getConfig().getIntegerList("black_market_hidden.index") != null) {
                                        for (int split_i : MMGlobalExchanges.instance.getConfig().getIntegerList("black_market_hidden.index")) {
                                            // 如果分隔后的索引不存在则跳过
                                            if (split_i < split.length) {
                                                StringBuilder sb = new StringBuilder();
                                                for (int r_i = 0; r_i < split[split_i].length(); r_i++) {
                                                    sb.append(hid.charAt(r.nextInt(hid.length())));
                                                }
                                                split[split_i] = sb.toString();
                                            }
                                        }
                                        lore.set(i, String.join(regex, split));
                                    } else {
                                        lore.set(i, String.join(regex, split));
                                    }
                                // 不需要分隔符直接整行掩盖
                                } else {
                                    StringBuilder sb = new StringBuilder();
                                    for (int r_i = 0; r_i < l.length(); r_i++) {
                                        sb.append(hid.charAt(r.nextInt(hid.length())));
                                    }
                                    lore.set(i, sb.toString());
                                }
                            }
                        }
                    }
                    lore.add("§f商品唯一ID: " + id);
                    lore.add("§f价格: " + price + " 时息");
                    MMGlobalExchanges.blackMarketFile.reload();
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
        lore.add("§f购买次数: " + playerData.getBlackBuyNumber() + " 次");
        lore.add("§f购买总价: " + playerData.getBlackBuyPrice() + " 时息");
        lore.add("§f出售次数: " + playerData.getBlackSellNumber() + " 次");
        lore.add("§f出售总价: " + playerData.getBlackSellPrice() + " 时息");
        lore.add("§f正在上架: " + playerData.getBlackLaunchItems().size() + " 件商品");
        lore.add("§f邮件: " + playerData.getBlackMailItems().size() + " 件商品");
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
        lore.add("当前页数: " + getPage(player));
        lore.add("总页数: " + ((MMGlobalExchanges.marketFile.getSize(player) - 1) / MMGlobalExchanges.instance.getConfig().getInt("market_max") + 1));
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
            if (MMGlobalExchanges.blackMarket.getPage(player) == 0) {
                continue;
            }
            if (MMGlobalExchanges.blackMarket.getPage(player) < MMGlobalExchanges.blackMarketFile.getItemPage(id)) {
                continue;
            }
            if (player.getOpenInventory().getTitle().equals(MMGlobalExchanges.instance.getConfig().getString("market.name"))) {
                MMGlobalExchanges.blackMarket.open(player);
            }
        }
    }
}
