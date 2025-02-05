package ru.vidoskim.bukkitApi.item;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@RequiredArgsConstructor
public class ItemListener implements Listener {


    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if(ItemActionService.acceptAction(event, event.getCurrentItem(), (Player) event.getWhoClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(ItemActionService.acceptAction(event, player.getInventory().getItemInMainHand(), player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInteractEntityEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if(ItemActionService.acceptAction(event, player.getInventory().getItemInMainHand(), player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if(ItemActionService.acceptAction(event, player.getInventory().getItemInMainHand(), player)) {
            event.setCancelled(true);
        }
    }
}
