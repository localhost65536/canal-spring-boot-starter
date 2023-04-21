package cn.king.canal.client.spring.boot.framework.util;

import java.lang.reflect.Field;

public class FieldUtil {

    /**
     * @author:
     * @time: 2023-04-20 23:54
     * @param: object
     * @param: fieldName
     * @param: value
     * @return: void
     * @description: 通过反射设置指定对象的指定属性为指定的值
     */
    public static void setFieldValue(Object object, String fieldName, String value) throws NoSuchFieldException, IllegalAccessException {
        Field field;
        try {
            field = object.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = object.getClass().getSuperclass().getDeclaredField(fieldName);
        }
        field.setAccessible(true);
        Class<?> type = field.getType();
        Object result = StringConvertUtil.convertType(type, value);
        field.set(object, result);
    }

}
