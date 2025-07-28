package me.unfear.Slayer.menus;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.unfear.Slayer.Language;
import me.unfear.Slayer.Main;
import me.unfear.Slayer.PlayerData;
import me.unfear.Slayer.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SlayerShopMenu {
    private static final Language lang = Main.inst.getLanguage();

    private static ArrayList<ItemStack> getShopItems(final PlayerData data) {
        final ArrayList<ItemStack> items = new ArrayList<>();
        for (final ShopItem shopItem : Main.inst.getSlayerLoader().getShopItems()) {
            if (!data.getShopItemsPurchased().containsKey(shopItem.getId()))
                data.getShopItemsPurchased().put(shopItem.getId(), 0);

            // can player buy more of this item? if no then don't show it
            if (data.getShopItemsPurchased().getOrDefault(shopItem.getId(), 0) < shopItem.getPurchases()
                    || shopItem.getPurchases() == -1) {
                final ItemStack item = shopItem.createItem();
                final ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    final List<String> lore = meta.getLore() == null? new ArrayList<>() : meta.getLore();
                    if (shopItem.getCost() > data.getPoints()) {
                        if(!lore.isEmpty()) {
                            lore.remove(lore.size() - 1);
                        }
                        lore.add(Main.inst.getLanguage().tooExpensive());
                    }
                    meta.setLore(lore);
                }
                item.setItemMeta(meta);
                items.add(item);
            }
        }

        return items;
    }

    public static ChestGui create(final Player player, final PlayerData data, final int page) {
        final ItemStack background = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        final ItemMeta backgroundMeta = background.getItemMeta();
        if (backgroundMeta != null) {
            backgroundMeta.setDisplayName(ChatColor.RED + " ");
            background.setItemMeta(backgroundMeta);
        }

        final ItemStack prevArrow = new ItemStack(Material.ARROW);
        final ItemMeta prevArrowMeta = prevArrow.getItemMeta();
        if (prevArrowMeta != null) {
            prevArrowMeta.setDisplayName(Main.inst.getLanguage().previousPage());
            prevArrow.setItemMeta(prevArrowMeta);
        }

        final ItemStack nextArrow = new ItemStack(Material.ARROW);
        final ItemMeta nextArrowMeta = nextArrow.getItemMeta();
        if (nextArrowMeta != null) {
            nextArrowMeta.setDisplayName(Main.inst.getLanguage().nextPage());
            nextArrow.setItemMeta(nextArrowMeta);
        }

        // points
        final ItemStack points = new ItemStack(Material.GOLD_NUGGET);
        final ItemMeta pointsMeta = points.getItemMeta();
        if (pointsMeta != null) {
            pointsMeta.setDisplayName(lang.slayerPoints(data.getPoints()));
            points.setItemMeta(pointsMeta);
        }

        // back
        final ItemStack backButton = new ItemStack(Material.ARROW);
        final ItemMeta backButtonMeta = backButton.getItemMeta();
        if (backButtonMeta != null) {
            backButtonMeta.setDisplayName(lang.shopGuiBackName());
            backButtonMeta.setLore(lang.shopGuiBackLore());
        }
        backButton.setItemMeta(backButtonMeta);

        final ChestGui gui = new ChestGui(6, Main.inst.getLanguage().shopGuiTitle());

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        final PaginatedPane pages = new PaginatedPane(0, 0, 9, 5);
        pages.populateWithItemStacks(getShopItems(data));
        pages.setOnClick(event -> {
            final ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null) return;

            // purchase item
            if(!currentItem.hasItemMeta()) return;

            final ItemMeta meta = currentItem.getItemMeta();
            if(meta == null) return;

            if(!meta.getPersistentDataContainer().has(Main.inst.itemKey(), PersistentDataType.INTEGER)) return;

            final int id = meta.getPersistentDataContainer().getOrDefault(Main.inst.itemKey(), PersistentDataType.INTEGER, -1);
            if(id == -1) return;

            final ShopItem shopItem = Main.inst.getSlayerLoader().getShopItem(id);
            if (shopItem == null)
                return;
            // can player buy more of this item? shouldn't be shown, but just in case
            if (data.getShopItemsPurchased().getOrDefault(shopItem.getId(), 0) >= shopItem.getPurchases()
                    && shopItem.getPurchases() != -1)
                return;
            // can player afford this item?
            if (data.getPoints() < shopItem.getCost()) return;

            // player is able to buy it, so buy it
            data.setPoints(data.getPoints() - shopItem.getCost());
            data.getShopItemsPurchased().put(shopItem.getId(), data.getShopItemsPurchased().get(shopItem.getId()) + 1);
            for (final String command : shopItem.getCommands()) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", event.getWhoClicked().getName()));
            }

            create(player, data, page).show(event.getWhoClicked());
            event.getWhoClicked().sendMessage(Main.inst.getLanguage().transactionComplete(shopItem.getCost()));

        });

        gui.addPane(pages);

        final OutlinePane backgroundPane = new OutlinePane(0, 5, 9, 1);
        backgroundPane.addItem(new GuiItem(background));
        backgroundPane.setRepeat(true);
        backgroundPane.setPriority(Pane.Priority.LOWEST);

        gui.addPane(backgroundPane);

        pages.setPage(page);
        gui.update();

        final StaticPane navigation = new StaticPane(0, 5, 9, 1);
        if (page > 0) {
            navigation.addItem(new GuiItem(prevArrow, event -> {
                if (pages.getPage() > 0) {
                    create(player, data, page - 1).show(event.getWhoClicked());
                }
            }), 6, 0);
        }

        if (pages.getPage() < pages.getPages() - 1) {
            navigation.addItem(new GuiItem(new ItemStack(nextArrow), event -> {
                if (pages.getPage() < pages.getPages() - 1) {
                    create(player, data, page + 1).show(event.getWhoClicked());
                }
            }), 7, 0);
        }

        final String backButtonCommand = Main.inst.getSlayerLoader().getShopBackCommand(player.getName());
        if (!backButtonCommand.equalsIgnoreCase("none")) {
            navigation.addItem(
                    new GuiItem(backButton, event -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), backButtonCommand)), 0, 0);
        }

        navigation.addItem(
                new GuiItem(points), 4, 0);

        gui.addPane(navigation);

        return gui;
    }
}
