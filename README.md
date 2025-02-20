# 🔧 Vidoskim API
## Содержание:
- [**_Maven_**](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#maven)
- - [Bukkit](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#bukkit)
- - [Common](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#common)
- - [Velocity](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#velocity)
- [**_Common module_**](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-common-module)
- - [MySQL](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-mysql)
- - [MariaDB](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-mariadb)
- - [Sqlite](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-sqlite)
- - [TestUser модель](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#testuser-%D0%BC%D0%BE%D0%B4%D0%B5%D0%BB%D1%8C)
- - [Service интерфейс](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#%EF%B8%8F%EF%B8%8F-service-%D0%B8%D0%BD%D1%82%D0%B5%D1%80%D1%84%D0%B5%D0%B9%D1%81)
- [**_Bukkit module_**](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-bukkit-module)
- - [LiteCommands](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-litecommands)
- - [MessagesService](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-messagesservice)
- - [ItemBuilder](https://github.com/sema1ary/vidoskim-api?tab=readme-ov-file#itembuilder)
- [**_Velocity module_**](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-velocity-module)
- - [LiteCommands](https://github.com/Vidoskim/vidoskim-api?tab=readme-ov-file#-litecommands-1)

## Maven:
Bukkit и Velocity содержат Common
1) Установите проект себе
2) Откройте проект и в maven откройте нужный вам модуль
3) Нажмите clean затем install

Дальше используйте приведенные ниже dependency

### Bukkit:
```xml
<dependency>
    <groupId>ru.vidoskim</groupId>
    <artifactId>bukkit-module</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Common:
```xml
<dependency>
    <groupId>ru.vidoskim</groupId>
    <artifactId>common-module</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Velocity:
```xml
<dependency>
    <groupId>ru.vidoskim</groupId>
    <artifactId>velocity-module</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 🧠 Common module:
Данный модуль содержит утилиты для работы с базой данных через ORM (ORMLite) и интерфейс Сервиса.
Методы для работы с базой данных различаются на 2 варианта:

### 🦾 MySQL:
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

### 🦾 MariaDB:
```java
public class MariaDBExample {
    
    private JdbcPooledConnectionSource connectionSource;

    public static void main(String[] args) { 
        connectionSource = ConnectionSourceUtil.connectMariaDB(
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

### 🦾 PostgreSQL:
```java
public class PostgreSQLExample {
    
    private JdbcPooledConnectionSource connectionSource;

    public static void main(String[] args) { 
        connectionSource = ConnectionSourceUtil.connectPostgreSQL(
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

### 🦿 Sqlite:
```java
public class SqliteExample {
    
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
😊 С lombok:
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
@DatabaseTable(tableName = "players", daoClass = TestUserDaoImpl.class)
public class TestUser {
    @DatabaseField(generatedId = true, unique = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String username;
}
```

😒 Без lombok:
```java
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players", daoClass = TestUserDaoImpl.class)
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

Для каждой модели должен быть DAO:
```java
// Long - id
public interface TestUserDao extends Dao<TestUser, Long> {
    TestUser save(TestUser user) throws SQLException;

    void saveAll(List<TestUser> users) throws SQLException;

    Optional<TestUser> findById(Long id) throws SQLException;

    Optional<TestUser> findByUsername(String username) throws SQLException;

    List<TestUser> findAll() throws SQLException;
}
```

Реализация:
```java
public class TestUserDaoImpl extends BaseDaoImpl<TestUser, Long> implements TestUserDao {
    public TestUserDaoImpl(ConnectionSource connectionSource, Class<TestUser> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public TestUser save(TestUser user) throws SQLException {
        createOrUpdate(user);
        return user;
    }

    @Override
    public void saveAll(List<TestUser> users) throws SQLException {
        callBatchTasks((Callable<Void>) () -> {
            for (TestUser user : users) {
                createOrUpdate(user);
            }
            return null;
        });
    }

    @Override
    public Optional<TestUser> findById(Long id) throws SQLException {
        TestUser result = queryForId(id);
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<TestUser> findByUsername(String username) throws SQLException {
        if (username == null) return Optional.empty();

        QueryBuilder<TestUser, Long> queryBuilder = queryBuilder();
        Where<TestUser, Long> where = queryBuilder.where();
        String columnName = "username";

        SelectArg selectArg = new SelectArg(SqlType.STRING, username.toLowerCase());
        where.raw("LOWER(" + columnName + ")" + " = LOWER(?)", selectArg);
        return Optional.ofNullable(queryBuilder.queryForFirst());
    }

    @Override
    public List<TestUser> findAll() throws SQLException {
        return queryForAll();
    }
}
```

### 🏋️‍♂️ Service интерфейс:
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

## 🔨 Bukkit module:
Данный модуль предназначен для bukkit-плагинов (Майнкрафт-а). Содержит в себе фабрику 
для регистрации команды через _**LiteCommands**_ (https://github.com/Rollczi/LiteCommands) и _**MessagesService**_, 
он позволяет легче простого добавить в плагин локализацию с возможностью перезагрузки сообщений.

### 📜 LiteCommands:
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
    
    // В кавычках - сообщения при ошибках liteCommands.
}
```

### 📚 MessagesService:
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

### ItemBuilder:
Позволяет создавать ItemStack-и без ItemMeta в удобном builder-е, скоро будут actions, для обработки кликов и прочего.
```java
public class ItemBuilderExample implements Listener {
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().setItemInMainHand(
                ItemBuilder.builder(Material.PLAYER_HEAD)
                .setName(Component.text("test"))
                // etc...
                .build()
        );
    }
}
```

## 💠 Velocity module:
Данный модуль предназначен для velocity-плагинов (Майнкрафт-а, прокси серверов). Содержит в себе фабрику
для регистрации команды через _**LiteCommands**_ (https://github.com/Rollczi/LiteCommands)

### 📜 LiteCommands: 
```java
//        new LiteCommandUtil().create(MessagesConfig.PREFIX,
//                "Неправильное использование",
//                        "Команда только для игроков",
//                        "Игрок не найден",
//
//                        new TestCommand() // Ваша команда
//        );
```