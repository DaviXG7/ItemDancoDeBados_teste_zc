package zc.teste.itemdancodebados.item;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Item {

    @Getter
    private ItemStack item;

    public Item(ItemStack item) {
        this.item = item;
    }

    public static Item fromItemStack(ItemStack item) {
        return new Item(item);
    }

    public static Item fromMaterial(Material material) {
        return new Item(new ItemStack(material));
    }

    public Item name (String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public Item lore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.stream(lore).map(l -> ChatColor.translateAlternateColorCodes('&', l)).collect(Collectors.toList()));
        item.setItemMeta(meta);
        return this;
    }

    public Item amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public Item durability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public Item data(MaterialData data) {
        item.setData(data);
        return this;
    }

    public Item data(byte data) {
        item.setDurability(new MaterialData(item.getType(), data).getData());
        return this;
    }

    public Item enchant(int enchantmentId, int level) {
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.getById(enchantmentId), level);
        return this;
    }

    public Item enchant(org.bukkit.enchantments.Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }


}
