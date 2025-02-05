package service;

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

    public void registerService(Class<?> serviceClass, Object service) {
        ((Service) service).enable();
        serviceMap.put(serviceClass, service);
    }
}
