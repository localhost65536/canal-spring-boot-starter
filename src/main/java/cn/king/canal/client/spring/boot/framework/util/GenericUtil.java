package cn.king.canal.client.spring.boot.framework.util;


import cn.king.canal.client.spring.boot.framework.handler.EntryHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenericUtil {

    private static Map<Class<? extends EntryHandler>, Class> cache = new ConcurrentHashMap<>();

    /**
     * @author:
     * @time: 2023-04-20 23:57
     * @param: object
     * @return: java.lang.Class<T>
     * @description: 获取泛型类型。
     * 输入是一个 EntryHandler 对象（或其子类），输出是该对象所实现的 EntryHandler 接口的泛型参数类型。
     * 例如：UserHandler 实现了 EntryHandler<User> 接口，那么输入 UserHandler 对象，输出 User 类型。
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getTableClass(EntryHandler object) {
        Class<? extends EntryHandler> handlerClass = object.getClass();
        Class tableClass = cache.get(handlerClass);
        if (tableClass == null) {
            // 获取当前类实现的所有接口的类型 
            Type[] interfacesTypes = handlerClass.getGenericInterfaces();
            for (Type t : interfacesTypes) {
                Class c = (Class) ((ParameterizedType) t).getRawType();
                if (c.equals(EntryHandler.class)) {
                    tableClass = (Class<T>) ((ParameterizedType) t).getActualTypeArguments()[0];
                    cache.putIfAbsent(handlerClass, tableClass);
                    return tableClass;
                }
            }
        }
        return tableClass;
    }


}
