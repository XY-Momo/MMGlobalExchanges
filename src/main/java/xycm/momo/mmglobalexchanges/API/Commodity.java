package xycm.momo.mmglobalexchanges.API;

import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.file.History;

/**
 * 获取历史记录物品信息
 */
public class Commodity {

    private static History history;

    public Commodity(String type) {
        history = new History(type);
    }

    public static ItemStack getItem(int id) {
        return history.getItem(id);
    }

    public static int getPrice(int id) {
        return history.getPrice(id);
    }

    public static String getSeller(int id) {
        return history.getSeller(id);
    }

    public static String getBuyer(int id) {
        return history.getBuyer(id);
    }
}
