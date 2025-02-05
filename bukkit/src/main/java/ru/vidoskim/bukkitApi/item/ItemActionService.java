package ru.vidoskim.bukkitApi.item;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ItemActionService {
    private static final Map<Event, Map<String, Consumer<Player>>> actions = new HashMap<>();

    public static void registerAction(Event event, String uuid, Consumer<Player> action) {
        Map<String, Consumer<Player>> tempMap = new HashMap<>();
        tempMap.put(uuid, action);
        actions.put(event, tempMap);
    }

    public static boolean acceptAction(Event event, ItemStack itemStack, Player player) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return false;
        }

        ItemMeta clickedItemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataHolder = clickedItemMeta.getPersistentDataContainer();

        if (!persistentDataHolder.has(NamespacedKey.minecraft("action"))) {
            return false;
        }

        String uuid = persistentDataHolder.get(NamespacedKey.minecraft("action"), PersistentDataType.STRING);
        Map<String, Consumer<Player>> eventMapKey = actions.get(event);

        if(eventMapKey != null && eventMapKey.containsKey(uuid)) {
            eventMapKey.get(uuid).accept((player));
            return true;
        }

        return false;
    }
}
