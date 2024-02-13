package dev.reyaan.utils;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

public class WidgetDataUtils {
    public static void copyExposedFields(Object source, Object destination) {
//        Field[] fields = source.getClass().getDeclaredFields();
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(source.getClass(), Expose.class);

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(source);
                System.out.println("Setting field " + field.getName() + " from " + source + " to " + destination + " with a value of " + value);
                field.set(destination, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
