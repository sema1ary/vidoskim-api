package service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
@SuppressWarnings("unused")
public class ServiceUtil {
    private final Map<Class<?>, Object> serviceMap = new HashMap<>();

    public Object getService(Class<?> serviceClass) {
        Object object = serviceMap.get(serviceClass);
        if(object instanceof Service) {
            return object;
        }
        return null;
    }

    private void registerService(Class<?> serviceClass, Object service) {
        ((Service) service).enable();
        serviceMap.put(serviceClass, service);
    }

    private  <D extends Dao<T, ?>, T> D getDao(JdbcPooledConnectionSource connectionSource, Class<T> daoClass) {
        return DaoManager.lookupDao(connectionSource, daoClass);
    }
}
