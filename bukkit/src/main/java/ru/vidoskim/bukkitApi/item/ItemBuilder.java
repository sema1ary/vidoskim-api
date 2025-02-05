package ru.vidoskim.bukkitApi.item;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ItemBuilder {
    private final ItemStack item;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final UUID uuid;

    public ItemBuilder(@NonNull Material material) {
        this.item = new ItemStack(material);
        this.uuid = UUID.randomUUID();
    }

    public ItemStack build() {
        return item;
    }

    public ItemBuilder setName(@NonNull String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(miniMessage.deserialize(name));
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setName(@NonNull Component name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(name);
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setComponentLore(@NonNull List<Component> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(@NonNull List<String> lore) {
        List<Component> componentList = new ArrayList<>();

        lore.forEach(string -> componentList.add(miniMessage.deserialize(string)));

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.lore(componentList);
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setPlayerHeadOwner(@NonNull OfflinePlayer player) {
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwningPlayer(player);

        item.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder addAction(@NonNull EventType event, @NonNull Consumer<Player> action) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("action"), PersistentDataType.STRING, uuid.toString());
        item.setItemMeta(itemMeta);

        ItemActionService.registerAction(event, uuid.toString(), action);
        return this;
    }

    public static ItemBuilder builder(Material material) {
        return new ItemBuilder(material);
    }
 }
