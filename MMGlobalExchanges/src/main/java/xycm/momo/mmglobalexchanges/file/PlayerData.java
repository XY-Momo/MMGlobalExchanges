package xycm.momo.mmglobalexchanges.file;

import org.bukkit.configuration.file.YamlConfiguration;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 处理玩家数据
 */
public class PlayerData {

    private final String playerName;
    private File dataFile;
    private YamlConfiguration dataConfig;

    /**
     * 构造函数
     * @param playerName 玩家名
     */
    public PlayerData(String playerName) {
        this.playerName = playerName;
        this.reload();
    }

    /**
     * 重载玩家数据文件
     */
    private void reload() {
        // 获取市场插件数据文件夹路径
        File pluginDataFolder = MMGlobalExchanges.instance.getDataFolder();
        if (pluginDataFolder == null) {
            throw new IllegalStateException("插件数据文件夹未初始化！");
        }

        // 创建 PlayerData 文件夹
        File playerDataFolder = new File(pluginDataFolder, "PlayerData");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs(); // 递归创建文件夹
        }

        // 创建以玩家名命名的 .yml 文件
        this.dataFile = new File(playerDataFolder, playerName + ".yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            try {
                dataFile.createNewFile();
                YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
                config.set("sellNumber", 0);
                config.set("sellPrice", 0);
                config.set("sellItems", null);
                config.set("buyNumber", 0);
                config.set("buyPrice", 0);
                config.set("buyItems", null);
                config.set("launchItems", null);
                config.set("black_sellNumber", 0);
                config.set("black_sellPrice", 0);
                config.set("black_sellItems", null);
                config.set("black_buyNumber", 0);
                config.set("black_buyPrice", 0);
                config.set("black_buyItems", null);
                config.set("black_launchItems", null);
                config.save(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    /**
     * 添加市场出售数量
     */
    public void addSellNumber() {
        this.dataConfig.set("sellNumber", getSellNumber() + 1);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加黑市出售数量
     */
    public void addBlackSellNumber() {
        this.dataConfig.set("black_sellNumber", getBlackSellNumber() + 1);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加市场出售总价
     * @param price 价格
     */
    public void addSellPrice(float price) {
        this.dataConfig.set("sellPrice", getSellPrice() + price);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加黑市出售总价
     * @param price 价格
     */
    public void addBlackSellPrice(float price) {
        this.dataConfig.set("black_sellPrice", getBlackSellPrice() + price);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 添加市场出售商品
     * @param id 商品ID
     */
    public void addSellItem(int id) {
        List<Integer> sellItems = getSellItems();
        sellItems.add(id);
        this.dataConfig.set("sellItems", sellItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加黑市出售商品
     * @param id 商品ID
     */
    public void addBlackSellItem(int id) {
        List<Integer> blackSellItems = getBlackSellItems();
        blackSellItems.add(id);
        this.dataConfig.set("black_sellItems", blackSellItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加市场购买数量
     */
    public void addBuyNumber() {
        this.dataConfig.set("buyNumber", getBuyNumber() + 1);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加黑市购买数量
     */
    public void addBlackBuyNumber() {
        this.dataConfig.set("black_buyNumber", getBlackBuyNumber() + 1);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加市场购买总价
     * @param price 价格
     */
    public void addBuyPrice(int price) {
        this.dataConfig.set("buyPrice", getBuyPrice() + price);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加黑市购买总价
     * @param price 价格
     */
    public void addBlackBuyPrice(int price) {
        this.dataConfig.set("black_buyPrice", getBlackBuyPrice() + price);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加市场购买商品
     * @param id 商品ID
     */
    public void addBuyItem(int id) {
        List<Integer> buyItems = getBuyItems();
        buyItems.add(id);
        this.dataConfig.set("buyItems", buyItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加黑市购买商品
     * @param id 商品ID
     */
    public void addBlackBuyItem(int id) {
        List<Integer> blackBuyItems = getBlackBuyItems();
        blackBuyItems.add(id);
        this.dataConfig.set("black_buyItems", blackBuyItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加市场上架商品
     * @param id 商品ID
     */
    public void addLaunchItem(int id) {
        List<Integer> launchItems = getLaunchItems();
        launchItems.add(id);
        this.dataConfig.set("launchItems", launchItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加黑市上架商品
     * @param id 商品ID
     */
    public void addBlackLaunchItem(int id) {
        List<Integer> blackLaunchItems = getBlackLaunchItems();
        blackLaunchItems.add(id);
        this.dataConfig.set("black_launchItems", blackLaunchItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加市场邮件商品
     * @param id 商品ID
     */
    public void addMailItem(int id) {
        List<Integer> mailItems = getMailItems();
        mailItems.add(id);
        this.dataConfig.set("mailItems", mailItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加黑市邮件商品
     * @param id 商品ID
     */
    public void addBlackMailItem(int id) {
        List<Integer> blackMailItems = getBlackMailItems();
        blackMailItems.add(id);
        this.dataConfig.set("black_mailItems", blackMailItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除市场上架商品
     * @param id 商品ID
     */
    public void removeLaunchItem(int id) {
        List<Integer> launchItems = getLaunchItems();
        launchItems.remove(launchItems.indexOf(id));
        this.dataConfig.set("launchItems", launchItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除黑市上架商品
     * @param id 商品ID
     */
    public void removeBlackLaunchItem(int id) {
        List<Integer> blackLaunchItems = getBlackLaunchItems();
        blackLaunchItems.remove(blackLaunchItems.indexOf(id));
        this.dataConfig.set("black_launchItems", blackLaunchItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除市场邮件商品
     * @param id 商品ID
     */
    public void removeMailItem(int id) {
        List<Integer> mailItems = getMailItems();
        mailItems.remove(mailItems.indexOf(id));
        this.dataConfig.set("mailItems", mailItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除黑市邮件商品
     * @param id 商品ID
     */
    public void removeBlackMailItem(int id) {
        List<Integer> blackMailItems = getBlackMailItems();
        blackMailItems.remove(blackMailItems.indexOf(id));
        this.dataConfig.set("black_mailItems", blackMailItems);
        try {
            this.dataConfig.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取市场出售数量
     * @return 出售数量
     */
    public int getSellNumber() {
        return this.dataConfig.getInt("sellNumber");
    }

    /**
     * 获取黑市出售数量
     * @return 出售数量
     */
    public int getBlackSellNumber() {
        return this.dataConfig.getInt("black_sellNumber");
    }

    /**
     * 获取市场出售总价
     * @return 出售总价
     */
    public long getSellPrice() {
        return this.dataConfig.getLong("sellPrice");
    }

    /**
     * 获取黑市出售总价
     * @return 出售总价
     */
    public long getBlackSellPrice() {
        return this.dataConfig.getLong("black_sellPrice");
    }

    /**
     * 获取市场出售商品
     * @return 出售商品
     */
    public List<Integer> getSellItems() {
        return this.dataConfig.getIntegerList("sellItems");
    }

    /**
     * 获取黑市出售商品
     * @return 出售商品
     */
    public List<Integer> getBlackSellItems() {
        return this.dataConfig.getIntegerList("black_sellItems");
    }

    /**
     * 获取市场购买数量
     * @return 购买数量
     */
    public int getBuyNumber() {
        return this.dataConfig.getInt("buyNumber");
    }

    /**
     * 获取黑市购买数量
     * @return 购买数量
     */
    public int getBlackBuyNumber() {
        return this.dataConfig.getInt("black_buyNumber");
    }

    /**
     * 获取市场购买总价
     * @return 购买总价
     */
    public long getBuyPrice() {
        return this.dataConfig.getLong("buyPrice");
    }

    /**
     * 获取黑市购买总价
     * @return 购买总价
     */
    public long getBlackBuyPrice() {
        return this.dataConfig.getLong("black_buyPrice");
    }

    /**
     * 获取市场购买商品
     * @return 购买商品
     */
    public List<Integer> getBuyItems() {
        return this.dataConfig.getIntegerList("buyItems");
    }

    /**
     * 获取黑市购买商品
     * @return 购买商品
     */
    public List<Integer> getBlackBuyItems() {
        return this.dataConfig.getIntegerList("black_buyItems");
    }

    /**
     * 获取市场上架商品
     * @return 上架商品
     */
    public List<Integer> getLaunchItems() {
        return this.dataConfig.getIntegerList("launchItems");
    }

    /**
     * 获取黑市上架商品
     * @return 上架商品
     */
    public List<Integer> getBlackLaunchItems() {
        return this.dataConfig.getIntegerList("black_launchItems");
    }

    /**
     * 获取市场邮件商品
     * @return 邮件商品
     */
    public List<Integer> getMailItems() {
        return this.dataConfig.getIntegerList("mailItems");
    }

    /**
     * 获取黑市邮件商品
     * @return 邮件商品
     */
    public List<Integer> getBlackMailItems() {
        return this.dataConfig.getIntegerList("black_mailItems");
    }
}
