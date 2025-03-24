package xycm.momo.mmglobalexchanges.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 处理历史记录文件
 */
public class History {

    private final String type;
    private File dataFile;
    private YamlConfiguration dataConfig;

    /**
     * 构造函数
     * @param type 历史记录类型(Market、BlackMarket)
     */
    public History(String type) {
        this.type = type;
        this.reload();
    }

    /**
     * 重载配置文件
     */
    private void reload() {
        this.dataFile = new File(MMGlobalExchanges.instance.getDataFolder(), "History.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    /**
     * 获取历史记录商品数量
     * @return 商品数量
     */
    public int getSize() {
        this.reload();
        return dataConfig == null || !dataConfig.contains(type) ? 0 : dataConfig.getConfigurationSection(type).getKeys(false).size();
    }

    /**
     * 获取历史记录商品
     * @param id 商品ID
     * @return 商品
     */
    public ItemStack getItem(int id) {
        if (dataConfig.contains(type + "." + id)) {
            return dataConfig.getItemStack(type + "." + id + ".Item");
        } else {
            return null;
        }
    }

    /**
     * 获取历史记录商品价格
     * @param id 商品ID
     * @return 商品价格
     */
    public int getPrice(int id) {
        if (dataConfig.contains(type + "." + id)) {
            return dataConfig.getInt(type + "." + id + ".Price");
        } else {
            return 0;
        }
    }

    /**
     * 获取历史记录商品卖家
     * @param id 商品ID
     * @return 卖家
     */
    public String getSeller(int id) {
        if (dataConfig.contains(type + "." + id)) {
            return dataConfig.getString(type + "." + id + ".Player");
        } else {
            return null;
        }
    }

    /**
     * 获取历史记录商品买家
     * @param id 商品ID
     * @return 买家
     */
    public String getBuyer(int id) {
        if (dataConfig.contains(type + "." + id)) {
            return dataConfig.getString(type + "." + id + ".Buyer");
        } else {
            return null;
        }
    }

    /**
     * 获取商品上架时间
     * @param id 商品ID
     * @return 上架时间
     */
    public String getLaunchTime(int id) {
        if (dataConfig.contains(type + "." + id)) {
            return dataConfig.getString(type + "." + id + ".LaunchTime").replace("T", " ");
        } else {
            return null;
        }
    }

    /**
     * 获取商品出售及购买时间
     * @param id 商品ID
     * @return 出售及购买时间
     */
    public String getSellTime(int id) {
        if (dataConfig.contains(type + "." + id)) {
            return dataConfig.getString(type + "." + id + ".SellTime").replace("T", " ");
        } else {
            return null;
        }
    }

    /**
     * 设置历史记录商品买家
     * @param id 商品ID
     * @param player 买家
     */
    public void setBuyer(int id, Player player) {
        dataConfig.set(type + "." + id + ".Buyer", player.getName());
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存历史记录商品出售时间
     * @param id 商品ID
     */
    public void setSellTime(int id) {
        dataConfig.set(type + "." + id + ".SellTime", LocalDateTime.now().toString());
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置商品下架时间
     * @param id 商品ID
     */
    public void setDelistTime(int id) {
        dataConfig.set(type + "." + id + ".DelistTime", LocalDateTime.now().toString());
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存历史记录商品
     * @param player 卖家
     * @param price 价格
     * @param item 商品
     */
    public void saveItem(Player player, int price, ItemStack item) {
        int size = this.getSize();
        dataConfig.set(type + "." + size + ".Player", player.getName());
        dataConfig.set(type + "." + size + ".Price", price);
        dataConfig.set(type + "." + size + ".Item", item);
        dataConfig.set(type + "." + size + ".Launching", true);
        dataConfig.set(type + "." + size + ".LaunchTime", LocalDateTime.now().toString());
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改商品是否正在交易
     * @param id 商品ID
     * @param launching 是否正在交易
     */
    public void changeLaunching(int id, boolean launching) {
        this.reload();
        dataConfig.set(type + "." + id + ".Launching", launching);
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
