package xycm.momo.mmglobalexchanges.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 处理黑市文件
 */
public class BlackMarketFile {
    private File dataFile;
    private YamlConfiguration dataConfig;
    private final History history = new History("BlackMarket");

    public BlackMarketFile() {
        this.reload();
    }

    /**
     * 重载配置文件
     */
    public void reload() {
        this.dataFile = new File(MMGlobalExchanges.instance.getDataFolder(), "BlackMarket.yml");
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
     * 获取该商品ID在黑市第几页
     * @param id 商品ID
     * @return 页码
     */
    public int getItemPage(int id) {
        this.reload();
        return ((dataConfig.getIntegerList("BlackMarket").indexOf(id) + 1) / MMGlobalExchanges.instance.getConfig().getInt("black_market_max")) + 1;
    }

    /**
     * 获取黑市商品总数，筛选玩家过滤词
     * @param player 对应玩家
     * @return 过滤后黑市商品总数
     */
    public int getSize(Player player) {
        this.reload();
        if (dataConfig == null || !dataConfig.contains("BlackMarket")) {
            return 0;
        }
        return loadItems(MMGlobalExchanges.blackMarket.getFilter(player)).size();
    }

    /**
     * 获取卖家
     * @param id 商品ID
     * @return 卖家名
     */
    public String getSolder(int id) {
        this.reload();
        if (dataConfig.contains("BlackMarket." + id)) {
            return dataConfig.getString("BlackMarket." + id + ".Player");
        } else {
            return null;
        }
    }

    /**
     * 获取上架商品
     * @param id 商品ID
     * @return 商品
     */
    public ItemStack getItem(int id) {
        this.reload();
        if (dataConfig.contains("BlackMarket." + id)) {
            return dataConfig.getItemStack("BlackMarket." + id + ".Item");
        } else {
            return null;
        }
    }

    /**
     * 删除上架商品
     * @param id 商品ID
     */
    public void removeItem(int id) {
        this.reload();
        if (dataConfig.contains("BlackMarket." + id)) {
            dataConfig.set("BlackMarket." + id, null);
            try {
                dataConfig.save(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存上架商品
     * @param player 卖家
     * @param price 价格
     * @param item 商品
     */
    public void saveItem(Player player, int price, ItemStack item) {
        int size = history.getSize();
        PlayerData playerData = new PlayerData(player.getName());
        playerData.addBlackLaunchItem(size);
        history.saveItem(player, price, item);
        dataConfig.set("BlackMarket." + size + ".Player", player.getName());
        dataConfig.set("BlackMarket." + size + ".Price", price);
        dataConfig.set("BlackMarket." + size + ".Item", item);
        dataConfig.set("BlackMarket." + size + ".Launching", true);
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有上架中的商品
     * @param name 过滤名
     * @return 过滤后商品
     */
    public Map<Integer, Map<Integer, ItemStack>> loadItems(String name) {
        this.reload();
        Map<Integer, Map<Integer, ItemStack>> id_items = new TreeMap<>();
        if (dataConfig.contains("BlackMarket")) {
            for (String key : dataConfig.getConfigurationSection("BlackMarket").getKeys(false)) {
                int id = Integer.parseInt(key);
                int price = dataConfig.getInt("BlackMarket." + id + ".Price");
                ItemStack item = dataConfig.getItemStack("BlackMarket." + id + ".Item");
                if (item == null) continue;
                if (name == null || name.isEmpty()) {
                    Map<Integer, ItemStack> items = new HashMap<>();
                    items.put(price, item);
                    id_items.put(id, items);
                } else {
                    if (item.getItemMeta().getDisplayName() != null) {
                        if (item.getItemMeta().getDisplayName().contains(name)) {
                            Map<Integer, ItemStack> items = new HashMap<>();
                            items.put(price, item);
                            id_items.put(id, items);
                        }
                    } else {
                        if (item.getType().toString().contains(name)) {
                            Map<Integer, ItemStack> items = new HashMap<>();
                            items.put(price, item);
                            id_items.put(id, items);
                        }
                    }
                }
            }
        }
        return id_items;
    }
}