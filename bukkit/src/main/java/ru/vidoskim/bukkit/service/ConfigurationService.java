package ru.vidoskim.bukkit.service;

import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import service.Service;

@SuppressWarnings("unused")
public interface ConfigurationService extends Service {
    void reload(@NonNull Plugin plugin);

    @NonNull <T> T get(@NonNull String index);
}
