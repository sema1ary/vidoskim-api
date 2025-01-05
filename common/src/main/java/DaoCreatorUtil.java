package util;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.SQLException;
import java.util.List;

@UtilityClass
@SuppressWarnings("unused")
public class DaoCreatorUtil {

    @SneakyThrows
    public void create(JdbcPooledConnectionSource connectionSource,  Class<?> modelClass) {
        DaoManager.createDao(connectionSource, modelClass);
    }

    public void createList(JdbcPooledConnectionSource connectionSource, List<Class<?>> modelClassList) {
        modelClassList.forEach(modelClass -> {
            try {
                DaoManager.createDao(connectionSource, modelClass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
