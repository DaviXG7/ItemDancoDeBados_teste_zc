package zc.teste.itemdancodebados;

import com.google.gson.Gson;
import lombok.Getter;
import zc.teste.itemdancodebados.item.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    @Getter
    private final Connection connection;

    private Gson gson = new Gson();

    public DatabaseManager() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itemdedados", "root", "root");

        this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS items (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lore TEXT, amount INT, durability SMALLINT, data TINYINT, enchants TEXT)").executeUpdate();
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public void saveItem(Item item) throws SQLException {

        String sql = "INSERT INTO items (name, lore, amount, durability, data, enchants) VALUES (?, ?, ?, ?, ?, ?)";

        this.connection.prepareStatement(sql)
                .setString(1, item.getItem().getItemMeta().getDisplayName())
                .set(2, gson.toJson(item.getItem().getItemMeta().getLore()))
                .setInt(3, item.getItem().getAmount())
                .setShort(4, item.getItem().getDurability())
                .setByte(5, item.getItem().getData())
                .setString(6, gson.toJson(item.getItem().getItemMeta().getEnchants()))
                .executeUpdate();

        this.connection.prepareStatement("INSERT INTO items (name, lore, amount, durability, data, enchants) VALUES ('" + item.getName() + "', '" + item.getLore() + "', " + item.getAmount() + ", " + item.getDurability() + ", " + item.getData() + ", '" + item.getEnchants() + "')").executeUpdate();
    }









}
