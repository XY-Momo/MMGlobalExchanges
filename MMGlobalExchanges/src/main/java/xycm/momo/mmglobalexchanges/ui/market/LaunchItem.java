package xycm.momo.mmglobalexchanges.ui.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.Chest;

import java.util.ArrayList;
import java.util.List;

/**
 * 上架物品界面
 */
public class LaunchItem extends Chest {

    private int slot;
    private int price;

    /**
     * 上架物品构造函数
     * @param title 上架物品标题
     * @param price 打开后显示的默认价格
     */
    public LaunchItem(String title, int price) {
        super(title, 54);
        this.price = price;
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     */
    private void init() {
        addPriceNum0();
        addPriceNum1();
        addPriceNum2();
        addPriceNum3();
        addPriceNum4();
        addPriceNum5();
        addPriceNum6();
        addPriceNum7();
        addPriceNum8();
        addPriceNum9();
        addPriceClear();
        addConfirm();
        addPriceBack();
        addReturn();
    }

    /**
     * 设置选择商品时的背包槽位，方便撤回原位
     * @param slot 槽位
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * 获取玩家设置价格
     * @return 价格
     */
    public int getPrice() {
        return price;
    }

    /**
     * 打开上架物品界面
     * @param player 对应玩家
     */
    public void open(Player player) {
        this.init();
        addItem(0, getItem(0));
        super.open(player);
    }

    /**
     * 打开上架物品界面
     * @param player 对应玩家
     * @param last_price 添加价格的最后一位数
     */
    public void open(Player player, int last_price) {
        this.init();
        addItem(0, getItem(0));
        addPriceShow(last_price);
        this.price = last_price;
        super.open(player);
    }

    /**
     * 添加价格最后一位数
     * @param player 对应玩家
     * @param num 设置的数字
     */
    public void addPriceNum(Player player, int num) {
        int oldNum = this.price;
        int newNum;
        if (oldNum == 0) {
            newNum = num;
            addPriceShow(newNum);
        } else {
            newNum = oldNum * 10 + num;
            addPriceShow(Math.min(newNum, 99999999));
        }
        open(player, Math.min(newNum, 99999999));
    }

    /**
     * 撤回价格最后一位数
     * @param player 对应玩家
     */
    public void subPriceNum(Player player) {
        if (this.price >= 10) {
            int oldNum = this.price;
            int newNum = oldNum / 10;
            addPriceShow(newNum);
            open(player, newNum);
        } else {
            addPriceShow(0);
            open(player, 0);
        }
    }

    /**
     * 撤回物品
     * @param player 对应玩家
     */
    public void returnItem(Player player) {
        if (this.slot >= 54 && this.slot <= 80) {
            player.getInventory().setItem(this.slot - 45, this.getItem(0));
        }
        if (this.slot >= 81) {
            player.getInventory().setItem(this.slot - 81, this.getItem(0));
        }
        this.removeItem(0);
        SelectItem selectItem = new SelectItem(MMGlobalExchanges.instance.getConfig().getString("market.select"));
        selectItem.open(player);
    }

    /* 价格数字键0 */
    private void addPriceNum0() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键0");
        item.setItemMeta(meta);
        this.addItem(1, item);
    }

    /* 价格数字键1 */
    private void addPriceNum1() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键1");
        item.setItemMeta(meta);
        this.addItem(2, item);
    }

    /* 价格数字键2 */
    private void addPriceNum2() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(2);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键2");
        item.setItemMeta(meta);
        this.addItem(3, item);
    }

    /* 价格数字键3 */
    private void addPriceNum3() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(3);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键3");
        item.setItemMeta(meta);
        this.addItem(4, item);
    }

    /* 价格数字键4 */
    private void addPriceNum4() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(4);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键4");
        item.setItemMeta(meta);
        this.addItem(5, item);
    }

    /* 价格数字键5 */
    private void addPriceNum5() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(5);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键5");
        item.setItemMeta(meta);
        this.addItem(6, item);
    }

    /* 价格数字键6 */
    private void addPriceNum6() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(6);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键6");
        item.setItemMeta(meta);
        this.addItem(7, item);
    }

    /* 价格数字键7 */
    private void addPriceNum7() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(7);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键7");
        item.setItemMeta(meta);
        this.addItem(8, item);
    }

    /* 价格数字键8 */
    private void addPriceNum8() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(8);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键8");
        item.setItemMeta(meta);
        this.addItem(9, item);
    }

    /* 价格数字键9 */
    private void addPriceNum9() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        item.setAmount(9);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格数字键9");
        item.setItemMeta(meta);
        this.addItem(10, item);
    }

    /* 价格清除键 */
    private void addPriceClear() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格清除键");
        item.setItemMeta(meta);
        this.addItem(11, item);
    }

    /* 确定键 */
    private void addConfirm() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getTitle() + "确定键");
        item.setItemMeta(meta);
        this.addItem(12, item);
    }

    /* 价格退格 */
    private void addPriceBack() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("价格退格");
        item.setItemMeta(meta);
        this.addItem(13, item);
    }

    /* 价格显示 */
    private void addPriceShow(int price) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.valueOf(price));
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add((int)(Math.round(MMGlobalExchanges.instance.getConfig().getDouble("tax_rate") * 100)) + "%");
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.addItem(14, item);
    }

    /* 返回 */
    private void addReturn() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getTitle() + "物品返回");
        item.setItemMeta(meta);
        this.addItem(46, item);
    }
}
