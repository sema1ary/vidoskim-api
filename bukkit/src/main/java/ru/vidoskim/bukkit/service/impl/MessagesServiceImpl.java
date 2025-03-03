package ru.vidoskim.bukkit.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import ru.vidoskim.bukkit.service.MessagesService;

import java.util.HashMap;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class MessagesServiceImpl implements MessagesService {
    private final Plugin plugin;
    private final HashMap<String, String> messageMap = new HashMap<>();

    @Override
    public void reload() {
        plugin.reloadConfig();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("messages");

        if(section == null) {
            System.out.println("The config does not contain a messages section.");
            return;
        }

        section.getKeys(false).forEach(string -> messageMap.put(string, plugin.getConfig()
                .getString("messages." + string)));
    }

    @Override
    public @NonNull String getMessage(@NonNull String index) {
        if(messageMap.containsKey(index)) {
            return messageMap.get(index);
        }

        return "Localization error: " + index;
    }
}
