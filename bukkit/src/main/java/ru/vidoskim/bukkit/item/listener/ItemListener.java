package ru.vidoskim.bukkit.item.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import ru.vidoskim.bukkit.item.registry.ItemActionsRegistry;
import ru.vidoskim.bukkit.item.type.ItemActions;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemListener implements Listener {

    private final Set<EntityDamageEvent.DamageCause> explodeDamageCauses = Set.of(
            EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,
            EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
    );

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        handleItemAction(event.getItem(), event, ItemActions::getInteractAction);
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        handleItemAction(event.getPlayer().getInventory().getItemInMainHand(), event, ItemActions::getEntityInteractAction);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        handleItemAction(event.getItemDrop().getItemStack(), event, ItemActions::getDropAction);
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        handleItemAction(event.getItem().getItemStack(), event, ItemActions::getPickupAction);
    }

    @EventHandler
    public void onExplode(EntityDamageEvent event) {
        if (!(event.getEntity().getType().equals(EntityType.DROPPED_ITEM))) {
            return;
        }

        if (explodeDamageCauses.contains(event.getCause())) {
            handleItemAction(((Item) event.getEntity()).getItemStack(), event, ItemActions::getExplodeAction);
        }
    }

    @EventHandler
    public void onItemBurn(EntityCombustEvent event) {
        if (!event.getEntity().getType().equals(EntityType.DROPPED_ITEM)) {
            return;
        }

        handleItemAction(((Item) event.getEntity()).getItemStack(), event, ItemActions::getBurnAction);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.isShiftClick()) {
            return;
        }

        if (event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()
                || event.getCursor().getType().isAir()) {
            return;
        }

        handleItemAction(event.getCurrentItem(), event, ItemActions::getInventoryClickAction);
    }

    @EventHandler
    public void onHeldItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItemStack = player.getInventory().getItem(event.getNewSlot());
        ItemStack previousItemStack = player.getInventory().getItem(event.getPreviousSlot());

        handleItemAction(newItemStack, event, ItemActions::getNewHeldAction);
        handleItemAction(previousItemStack, event, ItemActions::getPreviousHeldAction);
    }

    private <T> void handleItemAction(ItemStack itemStack, T event,
                                      Function<ItemActions, Consumer<T>> actionGetter) {
        ItemActions actions = ItemActionsRegistry.get(itemStack);
        actionGetter.apply(actions).accept(event);
    }
}
