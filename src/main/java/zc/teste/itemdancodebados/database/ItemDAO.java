package zc.teste.itemdancodebados.database;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import zc.teste.itemdancodebados.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ItemDAO {

    private final DatabaseManager databaseManager;
    private final Gson gson = new Gson();


    public CompletableFuture<Void> saveItem(Item item) {

        return CompletableFuture.runAsync(() -> {
            try {
                String sql = "INSERT INTO items(material, name, lore, amount, durability, data, enchants) VALUES (?, ?, ?, ?, ?, ?,?)";

                Map<String, Integer> enchants = item.getItem().getItemMeta().getEnchants().entrySet().stream().collect(
                        Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue)
                );


                PreparedStatement ps = this.databaseManager.getConnection().prepareStatement(sql);
                ps.setString(1, item.getItem().getType().toString());
                ps.setString(2, item.getItem().getItemMeta().getDisplayName());
                ps.setString(3, gson.toJson(item.getItem().getItemMeta().getLore()));
                ps.setInt(4, item.getItem().getAmount());
                ps.setShort(5, item.getItem().getDurability());
                ps.setByte(6, item.getItem().getData().getData());
                ps.setString(7, gson.toJson(enchants));

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

    public CompletableFuture<Void> deleteItem(int id) {
        return CompletableFuture.runAsync(() -> {
            try {
                String sql = "DELETE FROM items WHERE id = ?";

                PreparedStatement ps = this.databaseManager.getConnection().prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public CompletableFuture<Void> updateItem(int id, Item item) {
        return CompletableFuture.runAsync(() -> {
            try {
                String sql = "UPDATE items SET material = ?, name = ?, lore = ?, amount = ?, durability = ?, data = ?, enchants = ? WHERE id = ?";

                Map<String, Integer> enchants = item.getItem().getItemMeta().getEnchants().entrySet().stream().collect(
                        Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue)
                );

                PreparedStatement ps = this.databaseManager.getConnection().prepareStatement(sql);
                ps.setString(1, item.getItem().getType().toString());
                ps.setString(2, item.getItem().getItemMeta().getDisplayName());
                ps.setString(3, gson.toJson(item.getItem().getItemMeta().getLore()));
                ps.setInt(4, item.getItem().getAmount());
                ps.setShort(5, item.getItem().getDurability());
                ps.setByte(6, item.getItem().getData().getData());
                ps.setString(7, gson.toJson(enchants));
                ps.setInt(8, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Item> getItem(int id) {


        return CompletableFuture.supplyAsync(() -> {
            try {
                String sql = "SELECT * FROM items WHERE id = ?";

                PreparedStatement ps = this.databaseManager.getConnection().prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet query = ps.executeQuery();

                if (query.next()) {
                    Item item = Item.fromMaterial(Material.valueOf(query.getString("material")));
                    if (query.getString("name") != null) item.name(query.getString("name"));
                    List<String> lore = gson.fromJson(query.getString("lore"), List.class);
                    item.lore(lore == null ? new String[0] : lore.toArray(new String[0]));
                    item.amount(query.getInt("amount"));
                    item.durability((short) query.getInt("durability"));
                    item.data((byte) query.getInt("data"));
                    Map<String, Double> enchants = query.getString("enchants") == null ? null : gson.fromJson(query.getString("enchants"), Map.class);
                    if (enchants != null) enchants.forEach((enchantment, doubleNumber) -> item.enchant(Enchantment.getByName(enchantment), doubleNumber.intValue()));
                    return item;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });



    }



}
