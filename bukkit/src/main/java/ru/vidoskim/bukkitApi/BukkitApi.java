package ru.vidoskim.bukkitApi;

import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;
import ru.vidoskim.bukkitApi.item.ItemListener;

@Getter
public final class BukkitApi extends JavaPlugin {
    private HeadDatabaseAPI headDatabaseAPI;

    @Override
    public void onEnable() {
        if(getServer().getPluginManager().isPluginEnabled("HeadDatabase")) {
            headDatabaseAPI = new HeadDatabaseAPI();
        }

        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }
}
