package ru.vidoskim.bukkitApi.item;

import lombok.RequiredArgsConstructor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ItemListener implements Listener {
    private static final Map<String, Consumer<Player>> actions = new HashMap<>();

    public static boolean haveAction(String uuid) {
        return actions.containsKey(uuid);
    }

    public static void registerAction(String uuid, Consumer<Player> action) {
        actions.put(uuid, action);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("action"))) {
            String uuid = clickedItem.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("action"), PersistentDataType.STRING);
            if(actions.containsKey(uuid)) {
                actions.get(uuid).accept((Player) event.getWhoClicked());
                event.setCancelled(true);
            }
        }
    }
}
