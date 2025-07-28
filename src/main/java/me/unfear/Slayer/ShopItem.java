package me.unfear.Slayer;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ShopItem implements Comparable<ShopItem> {

    private final int id;
    private final String name;
    private final ArrayList<String> description;
    private final int cost;
    private final ArrayList<String> commands;
    private final Material material;
    private final int itemAmount;
    private final int purchases;

    public ShopItem(final int id, final String name, final ArrayList<String> description, final int cost, final ArrayList<String> commands,
                    final Material material, final int itemAmount, final int purchases) {
        super();
        this.id = id;
        this.name = Chat.format(name);
        this.description = new ArrayList<>(description.size());
        for (final String line : description) {
            this.description.add(Chat.format(line));
        }
        this.cost = cost;
        this.commands = commands;
        this.material = material;
        this.itemAmount = itemAmount;
        this.purchases = purchases;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public Material getMaterial() {
        return material;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public int getPurchases() {
        return purchases;
    }

    public ItemStack createItem() {
        final ItemStack item = new ItemStack(material, itemAmount);
        final ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            final ArrayList<String> lore = new ArrayList<>(description);

            lore.add("");
            lore.add(Main.inst.getLanguage().shopCost(cost));
            lore.add(Main.inst.getLanguage().clickToPurchase());
            meta.setLore(lore);
            meta.getPersistentDataContainer().set(Main.inst.itemKey(), PersistentDataType.INTEGER, id);

            item.setItemMeta(meta);
        }

        return item;
    }

    @Override
    public int compareTo(final ShopItem o) {
        return this.id - o.id;
    }
}
