package service;

import org.junit.jupiter.api.Test;
import ru.vidoskim.bukkit.service.MessagesService;

public class MessagesServiceTest {
    @Test
    void MessagesServiceEnable() {
        MessagesService messagesService = new MessagesService();
        messagesService.enable();
        messagesService.disable();
    }
}
