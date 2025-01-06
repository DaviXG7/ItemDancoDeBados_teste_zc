package zc.teste.itemdancodebados.inv;

import org.bukkit.Material;
import zc.teste.itemdancodebados.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemState {
    
    private String name;
    private Material material;
    private List<String> lore = new ArrayList<>();
    private int amount = 1;
    private short durability = 1;
    private byte data = (byte) 0;
    private List<String> enchants = new ArrayList<>();
    
    public Item create() {

    }
}
