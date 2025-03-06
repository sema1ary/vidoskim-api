package ru.vidoskim.bukkit.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import ru.vidoskim.bukkit.service.ConfigService;

import java.util.HashMap;

@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {
    private final Plugin plugin;
    private final HashMap<String, Object> configurationMap = new HashMap<>();

    @Override
    public void enable() {
        reload();
    }

    @Override
    public void reload() {
        plugin.reloadConfig();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("configuration");

        if(section == null) {
            System.out.println("The config does not contain a configuration section.");
            return;
        }

        section.getKeys(false).forEach(string -> configurationMap.put(string, plugin.getConfig()
                .get("configuration." + string)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> @NonNull T get(@NonNull String index) {
        return (T) configurationMap.get(index);
    }
}
