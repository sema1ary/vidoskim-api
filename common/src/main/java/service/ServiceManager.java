package service;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ServiceManager {
    private final static Map<Class<?>, Object> serviceMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> serviceClass) {
        T object = (T) serviceMap.get(serviceClass);
        if(object instanceof Service) {
            return object;
        }
        return null;
    }

    public static void registerService(Class<?> serviceClass, Object service) {
        ((Service) service).enable();
        serviceMap.put(serviceClass, service);
    }

    public static void disableServices() {
        serviceMap.forEach((aClass, object) -> {
            ((Service) object).disable();
        });
    }
}
