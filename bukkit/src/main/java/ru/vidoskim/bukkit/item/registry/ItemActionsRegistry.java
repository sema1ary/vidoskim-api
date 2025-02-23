package ru.vidoskim.bukkit.item.registry;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.vidoskim.bukkit.item.type.ItemActions;

import java.util.HashMap;
import java.util.Map;

import java.util.*;

@UtilityClass
public class ItemActionsRegistry {

    private static final String KEY = "item_action_registry_key";
    private static final Set<Material> NULL_MATERIALS = Set.of(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);

    private final Map<String, ItemActions> registry = new HashMap<>();

    public void add(ItemStack itemStack, ItemActions actions) {
        add(itemStack, actions, UUID.randomUUID().toString());
    }

    public void add(ItemStack itemStack, ItemActions actions, String id) {
        if (registry.containsKey(id)) {
            throw new RuntimeException("Id \"" + id + "\" is not unique");
        }
        add0(itemStack, actions, id);
    }

    public ItemActions get(ItemStack itemStack) {
        if (isValidItemStack(itemStack)) {
            return new ItemActions();
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        String id = itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft(KEY), PersistentDataType.STRING);
        return id != null ? get(id) : new ItemActions();
    }

    public ItemActions get(String id) {
        return registry.getOrDefault(id, new ItemActions());
    }

    private void add0(ItemStack itemStack, ItemActions actions, String uniqueID) {
        if (isValidItemStack(itemStack)) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft(KEY), PersistentDataType.STRING, uniqueID);
        itemStack.setItemMeta(itemMeta);
        registry.put(uniqueID, actions);
    }

    private boolean isValidItemStack(ItemStack itemStack) {
        return itemStack == null || itemStack.getItemMeta() == null || NULL_MATERIALS.contains(itemStack.getType());
    }
}

