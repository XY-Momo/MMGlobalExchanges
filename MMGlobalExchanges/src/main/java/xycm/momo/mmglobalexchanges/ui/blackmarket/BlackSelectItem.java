package xycm.momo.mmglobalexchanges.ui.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.ui.Chest;

import java.util.HashMap;
import java.util.Map;

/**
 * 选择上架商品
 */
public class BlackSelectItem extends Chest {

    private final Map<String, String> playerInfo = new HashMap<>();
    public BlackSelectItem(String title) {
        super(title, 54);
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     * @param player 对应玩家
     */
    private void init(Player player) {
        this.addReturn();
        this.addInfo(player);
    }

    /**
     * 获取玩家提示信息
     * @param player 对应玩家
     * @return 提示信息
     */
    public String getInfo(Player player) {
        return playerInfo.get(player.getName());
    }

    /**
     * 设置玩家提示信息
     * @param player 对应玩家
     * @param info 提示信息
     */
    public void setInfo(Player player, String info) { playerInfo.put(player.getName(), info); }

    /**
     * 打开选择商品界面
     * @param player 对应玩家
     */
    @Override
    public void open(Player player) {
        this.init(player);
        super.open(player);
    }

    /* 返回 */
    private void addReturn() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getTitle() + "物品返回");
        item.setItemMeta(meta);
        this.addItem(46, item);
    }

    /* 提示信息 */
    private void addInfo(Player player) {
        ItemStack item = new ItemStack(Material.PAPER);
        item.setAmount(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(getInfo(player) == null ? " " : getInfo(player));
        item.setItemMeta(itemMeta);
        addItem(52, item);
    }
}
