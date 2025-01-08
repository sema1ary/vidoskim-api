package ru.vidoskim.bukkitApi;

import org.bukkit.plugin.java.JavaPlugin;
import ru.vidoskim.bukkitApi.item.ItemListener;

@SuppressWarnings("unused")
public class BukkitApi extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }
}
