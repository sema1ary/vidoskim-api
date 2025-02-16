package ru.vidoskim.bukkit.util.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder setName(Component name) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder setLore(List<Component> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    @SuppressWarnings("all")
    public ItemBuilder setSkullOwner(String owner) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwner(owner);
        itemStack.setItemMeta(skullMeta);

        return this;
    }

    public ItemBuilder setSkullTexture(String texture) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "vidoskimApi");
        gameProfile.getProperties().put("textures", new Property("textures", texture));
        itemStack.setItemMeta(skullMeta);

        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
    }

    public static ItemBuilder builder (Material material) {
        return new ItemBuilder(material);
    }
}
