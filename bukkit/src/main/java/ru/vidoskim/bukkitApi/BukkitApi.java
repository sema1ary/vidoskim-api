package ru.vidoskim.bukkitApi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.vidoskim.bukkitApi.item.ItemBuilder;
import ru.vidoskim.bukkitApi.item.ItemListener;

@SuppressWarnings("unused")
public class BukkitApi extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

    public class TestListener implements Listener {
        @EventHandler
        private void onJoin(PlayerJoinEvent event) {
//            event.getPlayer().getInventory().addItem(ItemBuilder.)
        }
    }
}
