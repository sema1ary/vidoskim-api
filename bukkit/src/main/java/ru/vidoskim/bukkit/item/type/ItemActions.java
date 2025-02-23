package ru.vidoskim.bukkit.item.type;

import lombok.Data;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.function.Consumer;

@Data
public class ItemActions {
    private Consumer<PlayerInteractEvent> interactAction = playerInteractEvent -> {};
    private Consumer<PlayerInteractEntityEvent> entityInteractAction = playerInteractEntityEvent -> {};
    private Consumer<PlayerDropItemEvent> dropAction = playerDropItemEvent -> {};
    private Consumer<EntityPickupItemEvent> pickupAction = entityPickupItemEvent -> {};
    private Consumer<EntityDamageEvent> explodeAction = entityExplodeEvent -> {};
    private Consumer<EntityCombustEvent> burnAction = entityCombustEvent -> {};
    private Consumer<PlayerItemHeldEvent> newHeldAction = playerItemHeldEvent -> {};
    private Consumer<InventoryClickEvent> inventoryClickAction = inventoryClickEvent -> {};
    private Consumer<PlayerItemHeldEvent> previousHeldAction = playerItemHeldEvent -> {};
}
