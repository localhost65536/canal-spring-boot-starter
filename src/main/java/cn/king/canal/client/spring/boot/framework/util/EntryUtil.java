package cn.king.canal.client.spring.boot.framework.util;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EntryUtil {

    /**
     * 缓存字段名称和实体属性的对应关系。key 为实体类，value 为字段名称和实体属性的对应关系。
     * 例如：User 类中的 userName 字段对应数据库中的 user_name 字段。
     */
    private static Map<Class, Map<String, String>> cache = new ConcurrentHashMap<>();

    /**
     * 获取字段名称和实体属性的对应关系
     */
    public static Map<String, String> getFieldName(Class c) {
        Map<String, String> map = cache.get(c);
        if (Objects.isNull(map)) {
            List<Field> fields = FieldUtils.getAllFieldsList(c);
            map = fields.stream().filter(EntryUtil::notTransient)
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .collect(Collectors.toMap(EntryUtil::getColumnName, Field::getName));
            // key已存在则不put
            cache.putIfAbsent(c, map);
        }
        return map;
    }

    /**
     * @author:
     * @time: 2023-04-20 23:49
     * @param: field
     * @return: java.lang.String
     * @description: 字段驼峰命名转下划线
     */
    private static String getColumnName(Field field) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
    }

    /**
     * @author:
     * @time: 2023-04-20 23:50
     * @param: field
     * @return: boolean
     * @description: 判断是否是瞬态字段
     * Transient 注解表示该字段不需要进行持久化，即不需要将其存储到数据库中。在使用 ORM 框架（如 Hibernate）进行对象关系映射时，
     * 如果一个类中的某个字段不需要被映射为表的一列，可以使用 @Transient 注解来标注
     */
    private static boolean notTransient(Field field) {
        Transient annotation = field.getAnnotation(Transient.class);
        return annotation == null;
    }

}
