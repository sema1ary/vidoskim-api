package ru.vidoskim.bukkit.item.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UniqueIdGenerator {

    private long id = 0;

    public String generate() {
        ++id;
        return "UID_TIME_" + System.currentTimeMillis() + "_ID_" + id;
    }
}
