package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public class TableCreatorUtil {
    @SneakyThrows
    public void create(JdbcPooledConnectionSource connectionSource, Class<?>... modelClasses) {
        for(Class<?> modelClass: modelClasses) {
            TableUtils.createTableIfNotExists(connectionSource, modelClass);
        }
    }
}
