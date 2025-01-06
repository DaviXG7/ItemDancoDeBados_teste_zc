package zc.teste.itemdancodebados;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import zc.teste.itemdancodebados.command.ItemCommand;
import zc.teste.itemdancodebados.database.DatabaseManager;
import zc.teste.itemdancodebados.inv.GuiListener;

import java.sql.SQLException;

@Getter
public final class ItemDancoDeBados extends JavaPlugin {

    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {

        try {
            this.databaseManager = new DatabaseManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getCommand("item").setExecutor(new ItemCommand());
        getServer().getPluginManager().registerEvents(new GuiListener(), this);

    }

    @Override
    public void onDisable() {
        try {
            databaseManager.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Plugin shutdown logic
    }

    public static ItemDancoDeBados getInstance() {
        return getPlugin(ItemDancoDeBados.class);
    }
}
