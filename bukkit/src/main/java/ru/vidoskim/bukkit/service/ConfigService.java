package ru.vidoskim.bukkit.service;

import lombok.NonNull;
import service.Service;

@SuppressWarnings("unused")
public interface ConfigService extends Service {
    void reload();

    @NonNull
    <T> T get(@NonNull String index);
}
