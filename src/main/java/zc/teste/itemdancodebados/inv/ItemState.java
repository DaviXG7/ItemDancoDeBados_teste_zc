package zc.teste.itemdancodebados.inv;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

@Getter
@Setter
public class ItemState {
    
    private String name = "§aSem nome";
    private Material material = Material.STONE;
    private List<String> lore = new ArrayList<>();
    private int amount = 1;
    private short durability = 1;
    private byte data = (byte) 0;
    private Map<Enchantment, Integer> enchants = new HashMap<>();

}
