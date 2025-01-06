package zc.teste.itemdancodebados.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import zc.teste.itemdancodebados.ItemDancoDeBados;
import zc.teste.itemdancodebados.database.ItemDAO;
import zc.teste.itemdancodebados.inv.ItemCreatorGui;
import zc.teste.itemdancodebados.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ItemCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return true;
        }

        //create, give, delete, setItemInHand

        if (args.length == 0) {
            sender.sendMessage("§cUtilize /item <create> ou /item <give> <id> ou /item <delete> <id> ou /item <setItemInHand> <id item antigo> ou /item <addItemInHand>");
            return true;
        }

        Player player = (Player) sender;

        ItemDancoDeBados plugin = ItemDancoDeBados.getInstance();
        ItemDAO itemDAO = plugin.getDatabaseManager().getItemDAO();

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "create":
                    player.openInventory(new ItemCreatorGui().getInventory());
                    break;
                case "additeminhand":
                    ItemStack item = player.getInventory().getItemInHand();
                    if (item == null || item.getType().equals(Material.AIR)) {
                        player.sendMessage("§cVocê precisa estar segurando um item válido!");
                        return true;
                    }
                    player.sendMessage("§eSalvando item....");
                    itemDAO.saveItem(Item.fromItemStack(item)).thenRun(() -> player.sendMessage("§aItem salvo com sucesso!"));
                    break;
            }

            return true;
        }

        if (args.length != 2) {
            sender.sendMessage("§cUtilize /item <create> ou /item <give> <id> ou /item <delete> <id> ou /item <setItemInHand> <id item antigo> ou /id <addItemInHand>");
            return true;
        }
        int id = Integer.parseInt(args[1]);

        switch (args[0].toLowerCase()) {
            case "give":

                player.sendMessage("§eBuscando item....");
                itemDAO.getItem(id).thenAccept((item) -> {
                    if (item == null) {
                        player.sendMessage("§cItem não encontrado.");
                        return;
                    }
                    player.getInventory().addItem(item.getItem());
                    player.sendMessage("§aItem encontrado e adicionado ao inventário!");
                });

                break;
            case "delete":

                player.sendMessage("§eBuscando item....");
                itemDAO.deleteItem(id).thenRun(() -> player.sendMessage("§aItem deletado do banco com sucesso!"));
                break;
            case "setiteminhand":
                ItemStack item = player.getInventory().getItemInHand();

                if (item == null || item.getType().equals(Material.AIR)) {
                    player.sendMessage("§cVocê precisa estar segurando um item válido!");
                    return true;
                }

                player.sendMessage("§eSalvando item....");
                itemDAO.updateItem(id, Item.fromItemStack(item)).thenRun(() -> player.sendMessage("§aItem salvo com sucesso!"));

                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length == 1) {
            return Arrays.asList("create", "give", "delete", "setItemInHand", "addItemInHand");
        }

        return Collections.emptyList();
    }
}
