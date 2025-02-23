package ru.vidoskim.bukkit;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.vidoskim.bukkit.item.listener.ItemListener;

@Getter
public class BukkitApi extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }
}
