package util;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.SQLException;
import java.util.List;

@UtilityClass
@SuppressWarnings("unused")
public class TableCreatorUtil {

    @SneakyThrows
    public void create(JdbcPooledConnectionSource connectionSource, Class<?> modelClass) {
        TableUtils.createTableIfNotExists(connectionSource, modelClass);
    }

    public void createList(JdbcPooledConnectionSource connectionSource, List<Class<?>> modelClassList) {
        modelClassList.forEach(modelClass -> {
            try {
                TableUtils.createTableIfNotExists(connectionSource, modelClass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
