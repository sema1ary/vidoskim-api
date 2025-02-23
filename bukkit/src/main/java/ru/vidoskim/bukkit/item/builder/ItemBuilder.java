package ru.vidoskim.bukkit.item.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.vidoskim.bukkit.item.registry.ItemActionsRegistry;
import ru.vidoskim.bukkit.item.type.ItemActions;
import ru.vidoskim.bukkit.item.utility.ReflectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings({"deprecation", "unused"})
@RequiredArgsConstructor
public class ItemBuilder {

    public static ItemBuilder newBuilder(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    private final ItemStack itemStack;
    private final ItemActions actions = new ItemActions();

    public ItemBuilder setDurability(int durability) {
        itemStack.setDurability((byte) durability);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setUnbreakable(flag);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setMaterial(@NonNull Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        if (name == null) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(name));

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(String... loreArray) {
        if (loreArray == null) {
            return this;
        }

        setLore(Arrays.asList(loreArray));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (lore == null) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.lore(lore.stream()
                .map(Component::text)
                .collect(Collectors.toList()));

        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemBuilder addLore(@NonNull String lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> loreList = itemMeta.getLore();

        if (loreList == null) {
            loreList = new ArrayList<>();
        }

        loreList.add(lore);
        return setLore(loreList);
    }

    public ItemBuilder addLore(@NonNull String... loreArray) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> loreList = itemMeta.getLore();

        if (loreList == null) {
            loreList = new ArrayList<>();
        }

        loreList.addAll(Arrays.asList(loreArray));
        return setLore(loreList);
    }

    public ItemBuilder setGlowing(boolean glowing) {
        Enchantment glowEnchantment = Enchantment.ARROW_DAMAGE;

        if (glowing) {
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
            addEnchantment(glowEnchantment, 1);
        } else {
            removeItemFlag(ItemFlag.HIDE_ENCHANTS);
            removeEnchantment(glowEnchantment);
        }

        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (enchantment == null) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        if (enchantment == null) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!itemMeta.hasEnchant(enchantment) || itemMeta.hasConflictingEnchant(enchantment)) {
            return this;
        }

        itemMeta.removeEnchant(enchantment);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addCustomPotionEffect(PotionEffect potionEffect, boolean isAdd) {
        if (potionEffect == null) {
            return this;
        }

        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        potionMeta.addCustomEffect(potionEffect, isAdd);

        itemStack.setItemMeta(potionMeta);
        return this;
    }

    public ItemBuilder setMainPotionEffect(PotionEffectType potionEffectType) {
        if (potionEffectType == null) {
            return this;
        }

        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        potionMeta.setMainEffect(potionEffectType);

        itemStack.setItemMeta(potionMeta);
        return this;
    }

    public ItemBuilder setPotionColor(Color color) {
        if (color == null) {
            return this;
        }

        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        potionMeta.setColor(color);

        itemStack.setItemMeta(potionMeta);
        return this;
    }

    public ItemBuilder setPlayerSkull(OfflinePlayer player) {
        if (player == null) {
            return this;
        }

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(player);

        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setTextureValue(String texture) {
        if (texture == null) {
            return this;
        }

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "ItzStonlex");
        gameProfile.getProperties().put("textures", new Property("textures", texture));

        ReflectionUtil.setField(skullMeta, "profile", gameProfile);

        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        if (color == null) {
            return this;
        }

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);

        itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        if (itemFlag == null) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlag);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag itemFlag) {
        if (itemFlag == null) {
            return this;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!itemMeta.hasItemFlag(itemFlag)) {
            return this;
        }

        itemMeta.removeItemFlags(itemFlag);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    private <T> ItemBuilder setAction(Consumer<T> consumer, Consumer<Consumer<T>> setter) {
        setter.accept(consumer);
        return this;
    }

    public ItemBuilder onInteract(Consumer<PlayerInteractEvent> consumer) {
        return setAction(consumer, actions::setInteractAction);
    }

    public ItemBuilder onInteractEntity(Consumer<PlayerInteractEntityEvent> consumer) {
        return setAction(consumer, actions::setEntityInteractAction);
    }

    public ItemBuilder onBurn(Consumer<EntityCombustEvent> consumer) {
        return setAction(consumer, actions::setBurnAction);
    }

    public ItemBuilder onPickup(Consumer<EntityPickupItemEvent> consumer) {
        return setAction(consumer, actions::setPickupAction);
    }

    public ItemBuilder onExplode(Consumer<EntityDamageEvent> consumer) {
        return setAction(consumer, actions::setExplodeAction);
    }

    public ItemBuilder onDrop(Consumer<PlayerDropItemEvent> consumer) {
        return setAction(consumer, actions::setDropAction);
    }

    public ItemBuilder onNewHeld(Consumer<PlayerItemHeldEvent> consumer) {
        return setAction(consumer, actions::setNewHeldAction);
    }

    public ItemBuilder onPreviousHeld(Consumer<PlayerItemHeldEvent> consumer) {
        return setAction(consumer, actions::setPreviousHeldAction);
    }

    public ItemBuilder onClickInventory(Consumer<InventoryClickEvent> consumer) {
        return setAction(consumer, actions::setInventoryClickAction);
    }

    public ItemStack build() {
        ItemActionsRegistry.add(itemStack, actions);
        return itemStack;
    }
}