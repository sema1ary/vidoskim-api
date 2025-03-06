package ru.vidoskim.bukkit.util;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import org.bukkit.command.CommandSender;

@SuppressWarnings("all")
public class LiteCommandUtil {

    public LiteCommands<CommandSender> create(String prefix, Object... commands) {
        return LiteBukkitFactory.builder()
                .settings(settings -> settings
                        .fallbackPrefix(prefix)
                        .nativePermissions(true)
                )
                .commands(commands)
                .message(LiteBukkitMessages.INVALID_USAGE, "&cНеверное использование!")
                .message(LiteBukkitMessages.PLAYER_ONLY, "&cЭта команда только для игроков!")
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, "&cЭтот игрок не найден.")
                .message(LiteBukkitMessages.MISSING_PERMISSIONS, "&cУ вас недосточно прав.")
                .schematicGenerator(SchematicFormat.angleBrackets())
                .build();
    }

    public LiteCommands<CommandSender> create(String prefix, String invalidUsageMessage, String playerOnlyMessage,
                                              String playerNotFoundMessage, String missingPermissionsMessage, Object... commands) {
        return LiteBukkitFactory.builder()
                .settings(settings -> settings
                        .fallbackPrefix(prefix)
                        .nativePermissions(true)
                )
                .commands(commands)
                .message(LiteBukkitMessages.INVALID_USAGE, invalidUsageMessage)
                .message(LiteBukkitMessages.PLAYER_ONLY, playerOnlyMessage)
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, playerNotFoundMessage)
                .message(LiteBukkitMessages.MISSING_PERMISSIONS, missingPermissionsMessage)
                .schematicGenerator(SchematicFormat.angleBrackets())
                .build();
    }
}
