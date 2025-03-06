package ru.vidoskim.bukkit.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
@SuppressWarnings("unused")
public class LocationUtil {
    public String locationToString(@NonNull Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";"
                + location.getY() + ";" + location.getZ() + ";"
                + location.getYaw() + ";" + location.getPitch();
    }

    public Location stringToLocation(@NonNull String str) {
        String[] parts = str.split(";");

        World world = Bukkit.getWorld(parts[0]);
        if (world == null) {
            return null;
        }

        return new Location(world,
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Float.parseFloat(parts[4]),
                Float.parseFloat(parts[5])
        );
    }
}
