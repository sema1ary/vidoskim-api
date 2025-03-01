package ru.vidoskim.bukkit.service;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

@SuppressWarnings("unused")
public class ConfigurationService {
    private final  HashMap<String, Object> configurationMap = new HashMap<>();

    public void reload(@NonNull Plugin plugin) {
        plugin.reloadConfig();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("configuration");

        if(section == null) {
            System.out.println("The config does not contain a configuration section.");
            return;
        }

        section.getKeys(false).forEach(string -> configurationMap.put(string, plugin.getConfig()
                .get("configuration." + string)));
    }

    @SuppressWarnings("unchecked")
    public @NonNull <T> T get(@NonNull String index) {
        return (T) configurationMap.get(index);
    }
}
