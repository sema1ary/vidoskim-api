package ru.vidoskim.bukkitApi.item;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ItemActionService {
    private static final Map<EventType, Map<String, Consumer<Player>>> actions = new HashMap<>();

    public static void registerAction(EventType event, String uuid, Consumer<Player> action) {
        Map<String, Consumer<Player>> actionList = actions.get(event);

        if(actionList == null) actionList = new HashMap<>();

        actionList.put(uuid, action);

        actions.remove(event);
        actions.put(event, actionList);
    }

    public static boolean acceptAction(EventType event, ItemStack itemStack, Player player) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return false;
        }

        ItemMeta clickedItemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataHolder = clickedItemMeta.getPersistentDataContainer();

        if (!persistentDataHolder.has(NamespacedKey.minecraft("action"))) {
            return false;
        }

        String uuid = persistentDataHolder.get(NamespacedKey.minecraft("action"), PersistentDataType.STRING);
        Map<String, Consumer<Player>> consumerMap = actions.get(event);

        if(consumerMap.containsKey(uuid)) {
            consumerMap.get(uuid).accept(player);
            return true;
        }

        return false;
    }
}
