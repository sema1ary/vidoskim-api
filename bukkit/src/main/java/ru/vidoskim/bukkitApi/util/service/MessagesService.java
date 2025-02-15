package ru.vidoskim.bukkitApi.util.service;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import service.Service;

import java.util.HashMap;

@SuppressWarnings("unused")
public class MessagesService implements Service {
    private final HashMap<String, String> messageMap = new HashMap<>();

    public void reload(@NonNull Plugin plugin) {
        plugin.reloadConfig();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("messages");

        if(section == null) {
            plugin.getLogger().severe("config dont contains messages section.");
            return;
        }

        section.getKeys(false).forEach(string -> messageMap.put(string, plugin.getConfig()
                .getString("messages." + string)));
    }

    public @NonNull String getMessage(@NonNull String index) {
        if(messageMap.containsKey(index)) {
            return messageMap.get(index);
        }

        return "Localization error: " + index;
    }
}
