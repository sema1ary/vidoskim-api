package ru.vidoskim.bukkit.service.impl;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import ru.vidoskim.bukkit.service.ConfigurationService;

import java.util.HashMap;

@SuppressWarnings("unused")
public class ConfigurationServiceImpl implements ConfigurationService {
    private final  HashMap<String, Object> configurationMap = new HashMap<>();

    @Override
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
    @Override
    public @NonNull <T> T get(@NonNull String index) {
        return (T) configurationMap.get(index);
    }
}
