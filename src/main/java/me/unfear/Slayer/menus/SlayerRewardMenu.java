package me.unfear.Slayer.menus;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane.Priority;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.unfear.Slayer.Language;
import me.unfear.Slayer.Main;
import me.unfear.Slayer.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SlayerRewardMenu {
    private static final Language lang = Main.inst.getLanguage();

    public static ChestGui create(final Player player, final PlayerData data) {
        final ItemStack background = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        final ItemMeta backgroundMeta = background.getItemMeta();
        if (backgroundMeta != null) {
            backgroundMeta.setDisplayName(ChatColor.RED + " ");
            background.setItemMeta(backgroundMeta);
        }

        // back
        final ItemStack backButton = new ItemStack(Material.ARROW);
        final ItemMeta backButtonMeta = backButton.getItemMeta();
        if (backButtonMeta != null) {
            backButtonMeta.setDisplayName(lang.rewardGuiBackName());
            backButtonMeta.setLore(lang.rewardGuiBackLore());
        }
        backButton.setItemMeta(backButtonMeta);

        final ItemStack reward = new ItemStack(Material.GOLD_BLOCK);
        final ItemMeta rewardMeta = background.getItemMeta();
        rewardMeta.setDisplayName(lang.rewardGuiRewardName());
        rewardMeta.setLore(lang.rewardGuiRewardLore(data.getReward()));
        reward.setItemMeta(rewardMeta);

        final ChestGui gui = new ChestGui(3, lang.rewardGuiTitle());

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        final OutlinePane backgroundPane = new OutlinePane(9, 3, Priority.LOWEST);
        backgroundPane.addItem(new GuiItem(background));
        backgroundPane.setRepeat(true);
        gui.addPane(Slot.fromXY(0, 0), backgroundPane);

        final StaticPane backPane = new StaticPane(9, 3, Priority.NORMAL);
        final String backButtonCommand = Main.inst.getSlayerLoader().getRewardBackCommand(player.getName());
        if (!backButtonCommand.equalsIgnoreCase("none")) {
            backPane.addItem(
                    new GuiItem(backButton, event -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), backButtonCommand)), 0, 2);
        }
        gui.addPane(Slot.fromXY(0, 0), backPane);

        // Collect rewards button
        final StaticPane rewardPane = new StaticPane(1, 1, Priority.NORMAL);
        rewardPane.addItem(new GuiItem(reward, event -> {
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage(Main.inst.getLanguage().rewardClaimed());

            data.setTasksCompleted(data.getTasksCompleted() + 1);
            data.setPoints(data.getPoints() + data.getReward());
            data.setCurrentTask(null);

            if (Main.inst.getSlayerLoader().isClaimRewardOpensMainMenu()) {
                Bukkit.getScheduler().runTaskLater(Main.inst, () -> {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "slayer " + player.getName() + " -s");
                }, 1);
            }
        }), 0, 0);
        gui.addPane(Slot.fromXY(4, 1), rewardPane);

        return gui;
    }
}
