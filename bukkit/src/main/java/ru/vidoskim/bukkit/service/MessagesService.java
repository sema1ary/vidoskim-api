package ru.vidoskim.bukkit.service;

import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import service.Service;

@SuppressWarnings("unused")
public interface MessagesService extends Service {
    void reload(@NonNull Plugin plugin);

    @NonNull String getMessage(@NonNull String index);
}
