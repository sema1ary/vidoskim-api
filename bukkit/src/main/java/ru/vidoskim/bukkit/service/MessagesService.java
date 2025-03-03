package ru.vidoskim.bukkit.service;

import lombok.NonNull;
import service.Service;

@SuppressWarnings("unused")
public interface MessagesService extends Service {
    void reload();

    @NonNull String getMessage(@NonNull String index);
}
