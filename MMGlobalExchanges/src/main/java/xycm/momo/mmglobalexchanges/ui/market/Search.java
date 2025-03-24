package xycm.momo.mmglobalexchanges.ui.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.ui.Chest;

/**
 * 搜索界面(需要配合龙核界面)
 */
public class Search extends Chest {
    public Search(String title) {
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
        itemMeta.setDisplayName(MMGlobalExchanges.instance.getConfig().getString("market.search") + "返回");
        item.setItemMeta(itemMeta);
        addItem(1, item);
    }
}
