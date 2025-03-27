package xycm.momo.mmglobalexchanges.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * 箱子界面
 */
public class Chest {
    private String title;
    private final int size;
    private final Map<Integer, ItemStack> items;

    /**
     * 构造函数
     * @param title 界面标题
     * @param size 界面大小
     */
    public Chest(String title, int size) {
        this.title = title;
        this.size = size;
        this.items = new HashMap<>();
    }

    /**
     * 添加物品
     * @param slot 槽位
     * @param item 物品
     */
    public void addItem(int slot, ItemStack item) {
        if (slot >= 0 && slot < size) {
            items.put(slot, item);
        }
    }

    /**
     * 获取已添加物品
     * @param slot 槽位
     * @return 物品
     */
    public ItemStack getItem(int slot) {
        if (slot >= 0 && slot < size) {
            return items.get(slot);
        }
        return null;
    }

    /**
     * 移除已添加物品
     * @param slot 槽位
     */
    public void removeItem(int slot) {
        if (slot >= 0 && slot < size) {
            items.remove(slot);
        }
    }

    /**
     * 移除给出索引物品
     */
    public void clear(int start, int end) {
        for (int i = start; i < end; i++) {
            removeItem(i);
        }
    }

    /**
     * 移除所有物品
     */
    public void clearAll() {
        for (int i = 0; i < size; i++) {
            removeItem(i);
        }
    }

    /**
     * 获取界面标题
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置界面标题
     * @param title 标题
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * 打开界面
     * @param player 对应玩家
     */
    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, size, title);
        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        player.openInventory(inventory);
    }

    /**
     * 关闭界面
     * @param player 对应玩家
     */
    public void close(Player player) {
        player.closeInventory();
    }
}
