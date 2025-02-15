package ru.vidoskim.velocity.util;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import dev.rollczi.litecommands.velocity.LiteVelocityFactory;
import dev.rollczi.litecommands.velocity.LiteVelocityMessages;

@SuppressWarnings("all")
public class LiteCommandUtil {
    public LiteCommands<CommandSource> create(ProxyServer proxyServer, String prefix
            , String invalidUsageMessage, String playerOnlyMessage, String playerNotFoundMessage, Object... commands) {

        return LiteVelocityFactory.builder(proxyServer)
                .settings(settings -> settings
                        .nativePermissions(true)
                )
                .commands(commands)
                .message(LiteVelocityMessages.INVALID_USAGE, invalidUsageMessage)
                .message(LiteVelocityMessages.PLAYER_ONLY, playerOnlyMessage)
                .message(LiteVelocityMessages.PLAYER_NOT_FOUND, playerNotFoundMessage)
                .schematicGenerator(SchematicFormat.angleBrackets())
                .build();
    }
}
