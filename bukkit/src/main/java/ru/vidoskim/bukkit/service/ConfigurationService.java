package ru.vidoskim.bukkit.service;

import jdk.jfr.Experimental;
import lombok.NonNull;
import service.Service;

@Experimental
@SuppressWarnings("unused")
public interface ConfigurationService extends Service {
    void reload();

    @NonNull <T> T get(@NonNull String index);
}
