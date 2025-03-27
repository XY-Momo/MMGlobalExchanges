package xycm.momo.mmglobalexchanges.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xycm.momo.mmglobalexchanges.MMGlobalExchanges;
import xycm.momo.mmglobalexchanges.listener.blackmarket.BlackLaunchRecordListener;
import xycm.momo.mmglobalexchanges.listener.blackmarket.BlackMailListener;
import xycm.momo.mmglobalexchanges.listener.blackmarket.BlackPurchaseRecordListener;
import xycm.momo.mmglobalexchanges.listener.blackmarket.BlackSellRecordListener;
import xycm.momo.mmglobalexchanges.listener.market.LaunchRecordListener;
import xycm.momo.mmglobalexchanges.listener.market.MailListener;
import xycm.momo.mmglobalexchanges.listener.market.PurchaseRecordListener;
import xycm.momo.mmglobalexchanges.listener.market.SellRecordListener;
import xycm.momo.mmglobalexchanges.file.History;
import xycm.momo.mmglobalexchanges.ui.blackmarket.BlackSearch;
import xycm.momo.mmglobalexchanges.ui.market.Search;

import java.util.ArrayList;
import java.util.List;

public class CommandTab implements TabExecutor {
    public final String tip = "MMGlobalExchanges指令用法:\n" +
                              "打开全球交易所界面/mmge open ...\n" +
                              "获取全球交易所商品/mmge get ...\n" +
                              "重载插件/mmge reload";

    public final String openTip = "MMGlobalExchanges指令用法:\n" +
                                  "打开市场相关界面/mmge open market {选填具体名称}\n" +
                                  "打开黑市相关界面/mmge open blackmarket {选填具体名称}";

    public final String getTip = "MMGlobalExchanges指令用法:\n" +
                                 "获取市场商品/mmge get market <ID>\n" +
                                 "获取黑市商品/mmge get blackmarket <ID>\n";
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) { return true;}
        if (args.length == 0) {
            sender.sendMessage(tip);
        } else if (args.length == 1 && !args[0].isEmpty()) {
            if (args[0].equalsIgnoreCase("open")) {
                sender.sendMessage(openTip);
            } else if (args[0].equalsIgnoreCase("get")) {
                sender.sendMessage(getTip);
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (sender.isOp() || sender instanceof ConsoleCommandSender) {
                    MMGlobalExchanges.instance.saveDefaultConfig();
                    MMGlobalExchanges.instance.reloadConfig();
                    MMGlobalExchanges.instance.reload();
                    sender.sendMessage("默全球交易重载成功!");
                }
            } else {
                sender.sendMessage(tip);
            }
        } else if (args.length == 2 && !args[1].isEmpty()) {
            if (args[0].equalsIgnoreCase("open")) {
                if (args[1].equalsIgnoreCase("market")) {
                    MMGlobalExchanges.market.setPage((Player) sender, 1);
                    MMGlobalExchanges.market.open((Player) sender);
                } else if (args[1].equalsIgnoreCase("blackmarket")) {
                    MMGlobalExchanges.blackMarket.setPage((Player) sender, 1);
                    MMGlobalExchanges.blackMarket.open((Player) sender);
                } else {
                    sender.sendMessage(openTip);
                }
            }
        } else if (args.length == 3 && !args[2].isEmpty()) {
            if (args[0].equalsIgnoreCase("open")) {
                if (args[1].equalsIgnoreCase("market")) {
                    switch (args[2].toLowerCase()) {
                        case "market":
                            MMGlobalExchanges.market.setPage((Player) sender, 1);
                            MMGlobalExchanges.market.open((Player) sender);
                            break;
                        case "search":
                            Search search = new Search("市场搜索");
                            search.open((Player) sender);
                            break;
                        case "purchase_record":
                            PurchaseRecordListener.purchaseReturnInfo.put(sender.getName(), "市场");
                            MMGlobalExchanges.purchaseRecord.setPage((Player) sender, 1);
                            MMGlobalExchanges.purchaseRecord.open((Player) sender);
                            break;
                        case "sell_record":
                            SellRecordListener.sellReturnInfo.put(sender.getName(), "市场");
                            MMGlobalExchanges.sellRecord.setPage((Player) sender, 1);
                            MMGlobalExchanges.sellRecord.open((Player) sender);
                            break;
                        case "launch_record":
                            LaunchRecordListener.launchReturnInfo.put(sender.getName(), "市场");
                            MMGlobalExchanges.launchRecord.setPage((Player) sender, 1);
                            MMGlobalExchanges.launchRecord.open((Player) sender);
                            break;
                        case "mail":
                            MailListener.mailReturnInfo.put(sender.getName(), "市场");
                            MMGlobalExchanges.mail.setPage((Player) sender, 1);
                            MMGlobalExchanges.mail.open((Player) sender);
                            break;
                    }
                } else if (args[1].equalsIgnoreCase("blackmarket")) {
                    switch (args[2].toLowerCase()) {
                        case "blackmarket":
                            MMGlobalExchanges.blackMarket.setPage((Player) sender, 1);
                            MMGlobalExchanges.blackMarket.open((Player) sender);
                            break;
                        case "search":
                            BlackSearch search = new BlackSearch("黑市搜索");
                            search.open((Player) sender);
                        case "purchase_record":
                            BlackPurchaseRecordListener.purchaseReturnInfo.put(sender.getName(), "黑市");
                            MMGlobalExchanges.blackPurchaseRecord.setPage((Player) sender, 1);
                            MMGlobalExchanges.blackPurchaseRecord.open((Player) sender);
                            break;
                        case "sell_record":
                            BlackSellRecordListener.sellReturnInfo.put(sender.getName(), "黑市");
                            MMGlobalExchanges.blackSellRecord.setPage((Player) sender, 1);
                            MMGlobalExchanges.blackSellRecord.open((Player) sender);
                            break;
                        case "launch_record":
                            BlackLaunchRecordListener.launchReturnInfo.put(sender.getName(), "黑市");
                            MMGlobalExchanges.blackLaunchRecord.setPage((Player) sender, 1);
                            MMGlobalExchanges.blackLaunchRecord.open((Player) sender);
                            break;
                        case "mail":
                            BlackMailListener.mailReturnInfo.put(sender.getName(), "黑市");
                            MMGlobalExchanges.blackMail.setPage((Player) sender, 1);
                            MMGlobalExchanges.blackMail.open((Player) sender);
                            break;
                        default:
                            sender.sendMessage(openTip);
                            break;
                    }
                }
            } else if (args[0].equalsIgnoreCase("get")) {
                if (sender.isOp() || sender instanceof ConsoleCommandSender) {
                    switch (args[1].toLowerCase()) {
                        case "market":
                            try {
                                int id = Integer.parseInt(args[2]);
                                History history = new History("Market");
                                Player player = (Player) sender;
                                ItemStack item = history.getItem(id);
                                if (item == null) {
                                    sender.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "不存在该商品！");
                                } else {
                                    for (int i = 0; i < 36; i++) {
                                        if (player.getInventory().getItem(i) == null) {
                                            player.getInventory().setItem(i, item);
                                            break;
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                sender.sendMessage(MMGlobalExchanges.instance.getConfig().getString("placeholder") + "请输入正确的商品ID!");
                            }
                            break;
                        case "blackmarket":
                            break;
                        default:
                            sender.sendMessage(getTip);
                            break;
                    }
                }
            } else {
                sender.sendMessage(tip);
            }
        } else {
            sender.sendMessage(tip);
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.isOp()) { return null;}
        List<String> possibleCompletions = new ArrayList<>();
        List<String> Completions = new ArrayList<>();
        if (args.length == 1) {
            possibleCompletions.add("get");
            possibleCompletions.add("open");
            possibleCompletions.add("reload");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("get")) {
                possibleCompletions.add("market");
                possibleCompletions.add("blackmarket");
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("market")) {
                possibleCompletions.add("market");
                possibleCompletions.add("search");
                possibleCompletions.add("purchase_record");
                possibleCompletions.add("sell_record");
                possibleCompletions.add("launch_record");
                possibleCompletions.add("mail");
            } else if (args[1].equalsIgnoreCase("blackmarket")) {
                possibleCompletions.add("blackmarket");
                possibleCompletions.add("search");
                possibleCompletions.add("purchase_record");
                possibleCompletions.add("sell_record");
                possibleCompletions.add("launch_record");
                possibleCompletions.add("mail");
            }
        }
        for (String Completion : possibleCompletions) {
            if (Completion.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                Completions.add(Completion);
            }
        }
        return Completions;
    }
}
