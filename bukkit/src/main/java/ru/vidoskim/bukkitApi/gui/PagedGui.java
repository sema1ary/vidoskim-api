package ru.vidoskim.bukkitApi.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.vidoskim.bukkitApi.item.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class PagedGui {
    private final Component title;

    private final int size;

    private int currentPage = 0;

    private int currentSlot = 0;

    private final List<Inventory> inventories = new ArrayList<>();

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public abstract void createInventory();

    public PagedGui(Component title, int size) {
        this.title = title;
        this.size = size;
    }

    @SuppressWarnings("unused")
    public void open(Player player) {
        if (inventories.isEmpty()) return;

        Inventory mainPage = inventories.get(currentPage);
        Inventory lastPage = inventories.get(inventories.size() - 1);

        inventories.forEach(this::createBottomMenu);

        mainPage.setItem(mainPage.getSize() - 9, null);
        lastPage.setItem(lastPage.getSize() - 1, null);

        player.openInventory(mainPage);
    }

    @SuppressWarnings("unused")
    public void addItemToInventory(ItemStack itemStack) {
        if (inventories.isEmpty()) {
            createPage(itemStack);
            return;
        }

        if (size == 9 && currentSlot == 0) {
            currentSlot++;
            return;
        }

        Inventory page = inventories.get(inventories.size() - 1);
        if (!isMoreThanMax(page)) {
            page.setItem(currentSlot, itemStack);
            currentSlot++;
            return;
        }

        if (isMoreThanMax(page)) {
            createPage(itemStack);
        }
    }

    private boolean isMoreThanMax(Inventory page) {
        if (!(size == 9)) return currentSlot >= (page.getSize() - 9);
        return currentSlot >= (page.getSize() - 1);
    }

    private void createPage(ItemStack itemStack) {
        Inventory page = Bukkit.createInventory(null, size,
                title.append(Component.text(" - страница: " + (inventories.size() + 1))));
        currentSlot = 0;
        page.setItem(currentSlot, itemStack);
        currentSlot++;
        inventories.add(page);
    }

    private void createBottomMenu(Inventory page) {
        page.setItem(size - 9, ItemBuilder.builder("62699")
                .setName(miniMessage.deserialize("<gray>Назад"))
                .setClickAction(player -> {
                    changePage(player, currentPage - 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                })
                .build());

        page.setItem(size - 1, ItemBuilder.builder("62698")
                .setName(miniMessage.deserialize("<gray>Вперёд"))
                .setClickAction(player -> {
                    changePage(player, currentPage + 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                })
                .build());
    }

    private void changePage(Player player, int count) {
        currentPage = count;
        Inventory page = inventories.get(currentPage);
        player.openInventory(page);
    }
}
