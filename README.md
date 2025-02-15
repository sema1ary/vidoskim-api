# Vidoskim API

## Common module:
Данный модуль содержит утилиты для работы с базой данных через ORM (ORMLite) и интерфейс Сервиса.
Методы для работы с базой данных различаются на 2 варианта:

### MySQL:
```java
public class MySQLExample {
    
    private JdbcPooledConnectionSource connectionSource;

    public static void main(String[] args) { 
        connectionSource = ConnectionSourceUtil.connectMySQL(
                "host",
                "database",
                "username",
                "password",
                TestUserModel.class // Модели, пример:
                // TestUser.class, User.class
                // Пример модели TestUser ниже
        );
    }
}
```

### Sqlite:
```java
public class MySQLExample {
    
    private JdbcPooledConnectionSource connectionSource;

    public static void main(String[] args) { 
        connectionSource = ConnectionSourceUtil.connectSqlite(
                "filePath", // Путь до файла
                TestUserModel.class // Модели, пример:
                // TestUser.class, User.class
                // Пример модели TestUser ниже
        );
    }
}
```

### TestUser модель:
С lombok:
```java
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "players")
public class TestUser {
    @DatabaseField(generatedId = true, unique = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String username;
}
```

Без lombok:
```java
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players")
public class TestUser {
    @DatabaseField(generatedId = true, unique = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String username;

    public TestUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public TestUser() {
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
```

### Service интерфейс:
```java
public interface TestService extends Service {
    // etc...
}
```

Реализация:
```java
public class TestServiceImpl implements TestService {
    // Интерфейс добавляет enable() и disable()
    // (они необязательны)
    @Override
    public void enable() {
        
    }
    
    @Override
    public void disable() {
    }
    
    // etc...
}
```

В Main:
```java
public class ServiceExample {
    public static void main(String[] args) {
        TestService testService = new TestServiceImpl();
        testService.enable();
        testService.disable();
    }
}
```

## Bukkit module:
Данный модуль предназначен для bukkit-плагинов (Майнкрафт-а). Содержит в себе фабрику 
для регистрации команды через _**LiteCommands**_ (https://github.com/Rollczi/LiteCommands) и _**MessagesService**_, 
он позволяет легче простого добавить в плагин локализацию с возможностью перезагрузки сообщений.

### LiteCommands:
```java
public class LiteCommandsExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new LiteCommandUtil().create(MessagesConfig.PREFIX,
                "Неправильное использование",
                "Команда только для игроков",
                "Игрок не найден",

                new TestCommand() // Ваша команда
        );
    }
    
    // В кавычках - сообщения при ошибках liteCommnads.
}
```

### MessagesService:
```java
public class MessagesServiceExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        MessagesService messagesService = new MessagesService();
        
        messagesService.reload(this);
        
        String messageExample = messagesService.getMessage("message-example");
        
        this.getLogger().info(messageExample);
        
        // Если сообщение не будет найдено будет возвращено "Localization error: message-example";
        // Если не будет найдена секция messages будет выдана ошибка: The config does not contain a messages section.
    }
}
```
Пример config:
```yaml
messages:
  message-example: 'Test message'
```
Все сообщения обязательно должны быть в секции messages, иначе выдаст ошибку.
