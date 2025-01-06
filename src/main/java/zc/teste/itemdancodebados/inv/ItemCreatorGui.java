package zc.teste.itemdancodebados.inv;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import zc.teste.itemdancodebados.Item;

@Getter
public class ItemCreatorGui implements InventoryHolder {

    private final Inventory inventory;
    private ItemState state;
    private Item item;

    public ItemCreatorGui() {
        this.inventory = Bukkit.createInventory(this, 54, "§8Criador de Itens");

        this.item = Item.fromMaterial(Material.STONE);
        item.name("§aSem nome");
        item.lore("§7Sem descrição");

        inventory.setItem(13, item.getItem());

        inventory.setItem(28, Item.fromMaterial(Material.PAPER).name("§aColocar nome").getItem());
        inventory.setItem(29, Item.fromMaterial(Material.BOOK).name("§aColocar lore").getItem());
        inventory.setItem(30, Item.fromMaterial(Material.EMERALD).name("§aQuantidade").getItem());
        inventory.setItem(31, Item.fromMaterial(Material.STONE).name("§aMaterial").getItem());
        inventory.setItem(32, Item.fromMaterial(Material.DIAMOND).name("§aDurabilidade").getItem());
        inventory.setItem(33, Item.fromMaterial(Material.ENDER_PEARL).name("§aData").getItem());
        inventory.setItem(34, Item.fromMaterial(Material.ENCHANTED_BOOK).name("§aEncantar").getItem());

        inventory.setItem(49, Item.fromMaterial(Material.EMERALD_BLOCK).name("§aSalvar").getItem());

        this.state = new ItemState();
    }

    public void update() {
        this.item = Item.fromMaterial(state.getMaterial());
        item.name(state.getName());
        item.lore(state.getLore().toArray(new String[0]));
        item.amount(state.getAmount());
        item.durability(state.getDurability());
        item.data(state.getData());
        state.getEnchants().forEach((enchantment, integer) -> item.getItem().addUnsafeEnchantment(enchantment, integer));

        inventory.setItem(13, item.getItem());
    }


    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
