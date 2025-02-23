package ru.vidoskim.bukkit.item.utility;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

@SuppressWarnings("all")
@UtilityClass
public class ReflectionUtil {

    public Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?>[] primitiveTypes = Arrays.stream(parameterTypes).map(DataType::getPrimitive).toArray(Class<?>[]::new);
        return clazz.getConstructor(primitiveTypes);
    }

    public Object getValue(Object instance, Class<?> clazz, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return ReflectionUtil.getField(clazz, declared, fieldName).get(instance);
    }

    public Field getField(Class<?> clazz, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException {
        Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?>[] primitiveTypes = Arrays.stream(parameterTypes).map(DataType::getPrimitive).toArray(Class<?>[]::new);
        return clazz.getDeclaredMethod(methodName, primitiveTypes);
    }

    public void setField(Object object, String field, Object fieldValue) {
        Field f = null;
        Class<?> clazz = object.getClass();
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(object, fieldValue);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            assert f != null;
            f.setAccessible(false);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getField(Class<?> clazz, Object object, String field, Class<T> fieldType) {
        return (T) ReflectionUtil.getField(clazz, object, field);
    }

    public void setValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    public void setValue(Object instance, boolean declared, String fieldName, Object value) throws Exception {
        Field field;
        if (declared) {
            field = instance.getClass().getDeclaredField(fieldName);
        } else {
            field = instance.getClass().getField(fieldName);
        }
        field.setAccessible(true);
        field.set(instance, value);
    }

    public Object getField(Class<?> clazz, Object object, String field) {
        Field f = null;
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(object);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } finally {
            assert f != null;
            f.setAccessible(false);
        }
    }

    @Getter
    public enum DataType {
        BYTE(Byte.TYPE, Byte.class),
        SHORT(Short.TYPE, Short.class),
        INTEGER(Integer.TYPE, Integer.class),
        LONG(Long.TYPE, Long.class),
        CHARACTER(Character.TYPE, Character.class),
        FLOAT(Float.TYPE, Float.class),
        DOUBLE(Double.TYPE, Double.class),
        BOOLEAN(Boolean.TYPE, Boolean.class);

        private static final Map<Class<?>, DataType> CLASS_MAP;

        private final Class<?> primitive;
        private final Class<?> reference;

        DataType(Class<?> primitive, Class<?> reference) {
            this.primitive = primitive;
            this.reference = reference;
        }

        public static DataType fromClass(Class<?> clazz) {
            return CLASS_MAP.get(clazz);
        }

        public static Class<?> getPrimitive(Class<?> clazz) {
            DataType type = DataType.fromClass(clazz);
            return type == null ? clazz : type.getPrimitive();
        }

        public static Class<?> getReference(Class<?> clazz) {
            DataType type = DataType.fromClass(clazz);
            return type == null ? clazz : type.getReference();
        }

        public static Class<?>[] getPrimitive(Class<?>[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class<?>[] types = new Class<?>[length];
            for (int index = 0; index < length; ++index) {
                types[index] = DataType.getPrimitive(classes[index]);
            }
            return types;
        }

        public static Class<?>[] getReference(Class<?>[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class<?>[] types = new Class<?>[length];
            for (int index = 0; index < length; ++index) {
                types[index] = DataType.getReference(classes[index]);
            }
            return types;
        }

        public static Class<?>[] getPrimitive(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class<?>[] types = new Class<?>[length];
            for (int index = 0; index < length; ++index) {
                types[index] = DataType.getPrimitive(objects[index].getClass());
            }
            return types;
        }

        public static Class<?>[] getReference(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class<?>[] types = new Class<?>[length];
            for (int index = 0; index < length; ++index) {
                types[index] = DataType.getReference(objects[index].getClass());
            }
            return types;
        }

        public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {
            if (primary == null || secondary == null || primary.length != secondary.length) {
                return true;
            }
            for (int index = 0; index < primary.length; ++index) {
                Class<?> primaryClass = primary[index];
                Class<?> secondaryClass = secondary[index];
                if (primaryClass != secondaryClass && !primaryClass.isAssignableFrom(secondaryClass)) {
                    return true;
                }
            }
            return false;
        }

        static {
            Map<Class<?>, DataType> map = new HashMap<>();
            for (DataType type : DataType.values()) {
                map.put(type.getPrimitive(), type);
                map.put(type.getReference(), type);
            }
            CLASS_MAP = Collections.unmodifiableMap(map);
        }
    }
}
