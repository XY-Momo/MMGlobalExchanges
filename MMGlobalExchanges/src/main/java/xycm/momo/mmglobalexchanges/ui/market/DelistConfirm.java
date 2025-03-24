package xycm.momo.mmglobalexchanges.ui.market;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.file.History;
import xycm.momo.mmglobalexchanges.file.PlayerData;
import xycm.momo.mmglobalexchanges.thread.LaunchRecordInfo;
import xycm.momo.mmglobalexchanges.ui.Chest;

/**
 * 确认下架界面
 */
public class DelistConfirm extends Chest {

    private final History history = new History("Market");

    public DelistConfirm(String title) {
        super(title, 9);
    }

    /**
     * 初始化函数，用来处理添加功能物品逻辑
     */
    private void init() {
        addConfirm();
        addReturn();
    }

    /**
     * 打开去人下架界面
     * @param player 对应玩家
     */
    @Override
    public void open(Player player) {
        init();
        super.open(player);
    }

    /**
     * 确认下架
     * @param player 对应玩家
     */
    public void confirm(Player player) {
        int id = MMGlobalExchanges.delist.get(player.getName());
        if (MMGlobalExchanges.marketFile.getItem(id) == null) {
            if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                MMGlobalExchanges.launchRecord.setInfo(player, "下架失败，商品已被购买");
                returnLaunchRecord(player);
                LaunchRecordInfo.freshLaunchRecordInfo(player);
            } else {
                returnLaunchRecord(player);
                player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "下架失败，商品已被购买");
            }
        } else {
            for (int i = 0; i < 36; i++) {
                if (player.getInventory().getItem(i) == null) {
                    player.getInventory().setItem(i, history.getItem(id));
                    MMGlobalExchanges.marketFile.removeItem(id);
                    history.changeLaunching(id, false);
                    history.setDelistTime(id);
                    PlayerData playerData = new PlayerData(player.getName());
                    playerData.removeLaunchItem(id);
                    break;
                }
            }
            if (MMGlobalExchanges.instance.getConfig().getBoolean("info")) {
                MMGlobalExchanges.launchRecord.setInfo(player, "下架成功");
                returnLaunchRecord(player);
                LaunchRecordInfo.freshLaunchRecordInfo(player);
            } else {
                returnLaunchRecord(player);
                player.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "下架成功");
            }
            Market.freshMarket(id);
            MMGlobalExchanges.delist.remove(player.getName());
        }
    }

    /**
     * 返回个人上架记录
     * @param player 对应玩家
     */
    public void returnLaunchRecord(Player player) {
        MMGlobalExchanges.launchRecord.open(player);
    }

    /* 确定键 */
    private void addConfirm() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getTitle() + "确定键");
        item.setItemMeta(meta);
        this.addItem(0, item);
    }

    /* 返回键 */
    private void addReturn() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getTitle() + "返回键");
        item.setItemMeta(meta);
        this.addItem(1, item);
    }

}
