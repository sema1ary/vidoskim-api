package ru.vidoskim.bukkitApi.util;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import org.bukkit.command.CommandSender;

public class LiteCommandUtil {
    @SuppressWarnings("all")
    public LiteCommands<CommandSender> create(String prefix, String invalidUsageMessage, String playerOnlyMessage,
                                              String playerNotFoundMessage, Object... commands) {

        return LiteBukkitFactory.builder()
                .settings(settings -> settings
                        .fallbackPrefix(prefix)
                        .nativePermissions(true)
                )
                .commands(commands)
                .message(LiteBukkitMessages.INVALID_USAGE, invalidUsageMessage)
                .message(LiteBukkitMessages.PLAYER_ONLY, playerOnlyMessage)
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, playerNotFoundMessage)
                .schematicGenerator(SchematicFormat.angleBrackets())
                .build();
    }
}
