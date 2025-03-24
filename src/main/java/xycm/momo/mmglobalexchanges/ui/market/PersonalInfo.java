package xycm.momo.mmglobalexchanges.ui.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.file.PlayerData;
import xycm.momo.mmglobalexchanges.ui.Chest;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人信息界面
 */
public class PersonalInfo extends Chest {

    private PlayerData playerData;
    public PersonalInfo(String title) {
        super(title, 54);
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     * @param player 对应玩家
     */
    private void init(Player player) {
        this.playerData = new PlayerData(player.getName());
        addPurchaseRecord();
        addSellRecord();
        addPurchaseInfo(player);
        addSellInfo(player);
        addLaunchRecord();
        addMail();
        addReturn();
    }

    /**
     * 打开个人信息界面
     * @param player 对应玩家
     */
    @Override
    public void open(Player player) {
        init(player);
        super.open(player);
    }

    /* 购买记录 */
    private void addPurchaseRecord() {
        ItemStack item = new ItemStack(Material.BOOK);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "购买记录");
        item.setItemMeta(itemMeta);
        addItem(0, item);
    }

    /* 出售记录 */
    private void addSellRecord() {
        ItemStack item = new ItemStack(Material.BOOK);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "出售记录");
        item.setItemMeta(itemMeta);
        addItem(1, item);
    }

    /* 购买信息 */
    private void addPurchaseInfo(Player player) {
        ItemStack item = new ItemStack(Material.PAPER);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "购买信息");
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add("§f用户: " + player.getName());
        lore.add("§f购买次数: " + playerData.getBuyNumber());
        lore.add("§f购买总价: " + playerData.getBuyPrice() + " 时息");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        addItem(2, item);
    }

    /* 出售信息 */
    private void addSellInfo(Player player) {
        ItemStack item = new ItemStack(Material.PAPER);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "出售信息");
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add("§f用户: " + player.getName());
        lore.add("§f出售次数: " + playerData.getSellNumber());
        lore.add("§f出售总价: " + playerData.getSellPrice() + " 时息");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        addItem(3, item);
    }

    /* 正在上架物品 */
    private void addLaunchRecord() {
        ItemStack item = new ItemStack(Material.ANVIL);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "正在上架");
        item.setItemMeta(itemMeta);
        addItem(4, item);
    }

    /* 邮件 */
    private void addMail() {
        ItemStack item = new ItemStack(Material.ENDER_CHEST);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getTitle() + "邮件");
        item.setItemMeta(itemMeta);
        addItem(5, item);
    }

    /* 返回 */
    private void addReturn() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getTitle() + "返回键");
        item.setItemMeta(meta);
        this.addItem(46, item);
    }
}
