package xycm.momo.mmglobalexchanges.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 处理市场文件
 */
public class MarketFile {
    private File dataFile;
    private YamlConfiguration dataConfig;
    private final History history = new History("Market");

    public MarketFile() {
        this.reload();
    }

    /**
     * 重载配置文件
     */
    public void reload() {
        this.dataFile = new File(MMGlobalExchanges.instance.getDataFolder(), "Market.yml");
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
     * 获取该商品ID在市场第几页
     * @param id 商品ID
     * @return 页码
     */
    public int getItemPage(int id) {
        this.reload();
        return ((dataConfig.getIntegerList("Market").indexOf(id) + 1) / MMGlobalExchanges.instance.getConfig().getInt("market_max")) + 1;
    }

    /**
     * 获取市场商品总数，筛选玩家过滤词
     * @param player 对应玩家
     * @return 过滤后市场商品总数
     */
    public int getSize(Player player) {
        this.reload();
        if (dataConfig == null || !dataConfig.contains("Market")) {
            return 0;
        }
        return loadItems(MMGlobalExchanges.market.getFilter(player)).size();
    }

    /**
     * 获取卖家
     * @param id 商品ID
     * @return 卖家名
     */
    public String getSolder(int id) {
        this.reload();
        if (dataConfig.contains("Market." + id)) {
            return dataConfig.getString("Market." + id + ".Player");
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
        if (dataConfig.contains("Market." + id)) {
            return dataConfig.getItemStack("Market." + id + ".Item");
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
        if (dataConfig.contains("Market." + id)) {
            dataConfig.set("Market." + id, null);
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
        playerData.addLaunchItem(size);
        history.saveItem(player, price, item);
        dataConfig.set("Market." + size + ".Player", player.getName());
        dataConfig.set("Market." + size + ".Price", price);
        dataConfig.set("Market." + size + ".Item", item);
        dataConfig.set("Market." + size + ".Launching", true);
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有上架中的商品
     * @return 所有上架中的商品
     */
    public List<ItemStack> loadItems() {
        this.reload();
        List<ItemStack> items = new ArrayList<>();
        if (dataConfig.contains("Market")) {
            for (String key : dataConfig.getConfigurationSection("Market").getKeys(false)) {
                int id = Integer.parseInt(key);
                ItemStack item = dataConfig.getItemStack("Market." + id + ".Item");
                if (item == null) continue;
                items.add(item);
            }
        }
        return items;
    }

    /**
     * 获取所有上架中的商品(带id和价格)
     * @param name 过滤名
     * @return 过滤后商品
     */
    public Map<Integer, Map<Integer, ItemStack>> loadItems(String name) {
        this.reload();
        Map<Integer, Map<Integer, ItemStack>> id_items = new TreeMap<>();
        if (dataConfig.contains("Market")) {
            for (String key : dataConfig.getConfigurationSection("Market").getKeys(false)) {
                int id = Integer.parseInt(key);
                int price = dataConfig.getInt("Market." + id + ".Price");
                ItemStack item = dataConfig.getItemStack("Market." + id + ".Item");
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
                        } else {
                            if (item.getType().toString().toLowerCase().contains(name.toLowerCase())) {
                                Map<Integer, ItemStack> items = new HashMap<>();
                                items.put(price, item);
                                id_items.put(id, items);
                            }
                        }
                    } else {
                        if (item.getType().toString().toLowerCase().contains(name.toLowerCase())) {
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