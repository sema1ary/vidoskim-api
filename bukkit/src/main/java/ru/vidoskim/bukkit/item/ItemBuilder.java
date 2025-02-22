package ru.vidoskim.bukkit.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
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

    @SuppressWarnings("all")
    public ItemBuilder setSkullTexture(String value, String signature) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), "vidoskimApi");
        profile.setProperty(new ProfileProperty("textures", value, signature));

        skullMeta.setOwnerProfile(profile);

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
