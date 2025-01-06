package zc.teste.itemdancodebados.database;

import lombok.Getter;
import zc.teste.itemdancodebados.ItemDancoDeBados;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DatabaseManager {

    private final Connection connection;
    private final ItemDAO itemDAO;

    public DatabaseManager() throws SQLException {

        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itemdedados?characterEncoding=utf8", "root", "root");

        this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS items (id INT PRIMARY KEY AUTO_INCREMENT, material VARCHAR(156), name VARCHAR(156), lore TEXT, amount INT, durability SMALLINT, data TINYINT, enchants TEXT)").executeUpdate();

        this.itemDAO = new ItemDAO(this);
    }

    public void close() throws SQLException {
        this.connection.close();
    }









}
