package ru.vidoskim.bukkitApi.item;

import lombok.NonNull;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ItemBuilder {
    private String headId;
    private Material material;
    private Component name;
    private List<Component> lore;

    private Consumer<Player> clickAction;

    private final HeadDatabaseAPI headDatabaseAPI = new HeadDatabaseAPI();

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(String headId) {
        this.headId = headId;
    }

    public ItemBuilder setName(@NonNull Component name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setLore(@NonNull List<Component> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setClickAction(@NonNull Consumer<Player> action) {
        this.clickAction = action;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack;
        if(material != null) itemStack = new ItemStack(material);
        else itemStack = headDatabaseAPI.getItemHead(headId);

        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null) {
            if (name != null) meta.displayName(name);
            if (lore != null) meta.lore(lore);

            String uuid = UUID.randomUUID().toString();
            if(clickAction != null && !ItemListener.haveAction(uuid)) {
                meta.getPersistentDataContainer().set(NamespacedKey.minecraft("action"), PersistentDataType.STRING, uuid);
                ItemListener.registerAction(uuid, clickAction);
            }
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemBuilder builder(@NonNull Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder builder(@NonNull String headId) {
        return new ItemBuilder(headId);
    }
}