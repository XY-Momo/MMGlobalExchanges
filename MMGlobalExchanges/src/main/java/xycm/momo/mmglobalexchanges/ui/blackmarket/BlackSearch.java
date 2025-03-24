package xycm.momo.mmglobalexchanges.ui.blackmarket;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.Chest;

/**
 * 搜索界面(需要配合龙核界面)
 */
public class BlackSearch extends Chest {
    public BlackSearch(String title) {
        super(title, 54);
    }

    public void init() {
        addReturn();
    }

    @Override
    public void open(Player player) {
        init();
        super.open(player);
    }

    /* 返回 */
    public void addReturn() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(MMGlobalExchanges.instance.getConfig().getString("black_market.search") + "返回");
        item.setItemMeta(itemMeta);
        addItem(1, item);
    }
}
