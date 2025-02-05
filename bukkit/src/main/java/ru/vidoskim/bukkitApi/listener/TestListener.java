package ru.vidoskim.bukkitApi.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.vidoskim.bukkitApi.item.EventType;
import ru.vidoskim.bukkitApi.item.ItemBuilder;

public class TestListener implements Listener {
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(ItemBuilder.builder(Material.BOOK)
                .setName("TEST")
                .addAction(EventType.INVENTORY_CLICK, player -> player.sendMessage(Component.text("клик")
                ))
                .build());
    }
}
