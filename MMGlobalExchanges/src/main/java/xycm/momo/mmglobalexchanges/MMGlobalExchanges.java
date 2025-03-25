package xycm.momo.mmglobalexchanges;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xycm.momo.mmglobalexchanges.cmd.CommandTab;
import xycm.momo.mmglobalexchanges.dragoncore.BlackSearch;
import xycm.momo.mmglobalexchanges.dragoncore.Search;
import xycm.momo.mmglobalexchanges.listener.blackmarket.*;
import xycm.momo.mmglobalexchanges.listener.market.*;
import xycm.momo.mmglobalexchanges.file.BlackMarketFile;
import xycm.momo.mmglobalexchanges.file.MarketFile;
import xycm.momo.mmglobalexchanges.ui.blackmarket.*;
import xycm.momo.mmglobalexchanges.ui.market.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class MMGlobalExchanges extends JavaPlugin {

    public static MMGlobalExchanges instance;
    // 市场相关静态变量
    public static MarketFile marketFile;
    public static Market market;
    public static Map<String, LaunchItem> launchItems;
    public static Map<String, Map<Integer, Integer>> purchase;
    public static Map<String, Integer> delist;
    public static PurchaseRecord purchaseRecord;
    public static SellRecord sellRecord;
    public static LaunchRecord launchRecord;
    public static Mail mail;
    // 黑市相关静态变量
    public static BlackMarketFile blackMarketFile;
    public static BlackMarket blackMarket;
    public static Map<String, BlackLaunchItem> blackLaunchItems;
    public static Map<String, Map<Integer, Integer>> blackPurchase;
    public static Map<String, Integer> blackDelist;
    public static BlackPurchaseRecord blackPurchaseRecord;
    public static BlackSellRecord blackSellRecord;
    public static BlackLaunchRecord blackLaunchRecord;
    public static BlackMail blackMail;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Objects.requireNonNull(Bukkit.getPluginCommand("mmge")).setExecutor(new CommandTab());
        Objects.requireNonNull(Bukkit.getPluginCommand("mmge")).setTabCompleter(new CommandTab());
        marketFile = new MarketFile();
        market = new Market(getConfig().getString("market.name"));
        launchItems = new HashMap<>();
        purchase = new HashMap<>();
        delist = new HashMap<>();
        purchaseRecord = new PurchaseRecord(getConfig().getString("market.personal.purchase"));
        sellRecord = new SellRecord(getConfig().getString("market.personal.sell"));
        launchRecord = new LaunchRecord(getConfig().getString("market.personal.launch"));
        mail = new Mail(getConfig().getString("market.personal.mail"));
        getServer().getPluginManager().registerEvents(new MarketListener(), this);
        getServer().getPluginManager().registerEvents(new SelectItemListener(), this);
        getServer().getPluginManager().registerEvents(new LaunchItemListener(), this);
        getServer().getPluginManager().registerEvents(new PurchaseConfirmListener(), this);
        getServer().getPluginManager().registerEvents(new PersonalInfoListener(), this);
        getServer().getPluginManager().registerEvents(new PurchaseRecordListener(), this);
        getServer().getPluginManager().registerEvents(new SellRecordListener(), this);
        getServer().getPluginManager().registerEvents(new LaunchRecordListener(), this);
        getServer().getPluginManager().registerEvents(new DelistConfirmListener(), this);
        getServer().getPluginManager().registerEvents(new SearchListener(), this);
        getServer().getPluginManager().registerEvents(new MailListener(), this);
        getServer().getPluginManager().registerEvents(new Search(), this);
        blackMarketFile = new BlackMarketFile();
        blackMarket = new BlackMarket(getConfig().getString("black_market.name"));
        blackLaunchItems = new HashMap<>();
        blackPurchase = new HashMap<>();
        blackDelist = new HashMap<>();
        blackPurchaseRecord = new BlackPurchaseRecord(getConfig().getString("black_market.personal.purchase"));
        blackSellRecord = new BlackSellRecord(getConfig().getString("black_market.personal.sell"));
        blackLaunchRecord = new BlackLaunchRecord(getConfig().getString("black_market.personal.launch"));
        blackMail = new BlackMail(getConfig().getString("black_market.personal.mail"));
        getServer().getPluginManager().registerEvents(new BlackMarketListener(), this);
        getServer().getPluginManager().registerEvents(new BlackSelectItemListener(), this);
        getServer().getPluginManager().registerEvents(new BlackLaunchItemListener(), this);
        getServer().getPluginManager().registerEvents(new BlackPurchaseConfirmListener(), this);
        getServer().getPluginManager().registerEvents(new BlackPersonalInfoListener(), this);
        getServer().getPluginManager().registerEvents(new BlackPurchaseRecordListener(), this);
        getServer().getPluginManager().registerEvents(new BlackSellRecordListener(), this);
        getServer().getPluginManager().registerEvents(new BlackLaunchRecordListener(), this);
        getServer().getPluginManager().registerEvents(new BlackDelistConfirmListener(), this);
        getServer().getPluginManager().registerEvents(new BlackSearchListener(), this);
        getServer().getPluginManager().registerEvents(new BlackMailListener(), this);
        getServer().getPluginManager().registerEvents(new BlackSearch(), this);
        getLogger().info("默全球交易加载成功 QQ:1756784800");
    }

    public void reload() {
        market.setTitle(getConfig().getString("market.name"));
        purchaseRecord.setTitle(getConfig().getString("market.personal.purchase"));
        sellRecord.setTitle(getConfig().getString("market.personal.sell"));
        launchRecord.setTitle(getConfig().getString("market.personal.launch"));
        mail.setTitle(getConfig().getString("market.personal.mail"));

        blackMarket.setTitle(getConfig().getString("black_market.name"));
        blackPurchaseRecord.setTitle(getConfig().getString("black_market.personal.purchase"));
        blackSellRecord.setTitle(getConfig().getString("black_market.personal.sell"));
        blackLaunchRecord.setTitle(getConfig().getString("black_market.personal.launch"));
        blackMail.setTitle(getConfig().getString("black_market.personal.mail"));
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory().getTitle().equals(MMGlobalExchanges.instance.getConfig().getString("market.launch"))) {
                LaunchItem launchItem = MMGlobalExchanges.launchItems.get(player.getName());
                launchItem.returnItem(player);
            }
        }
    }
}
