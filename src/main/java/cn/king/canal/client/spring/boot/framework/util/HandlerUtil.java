package cn.king.canal.client.spring.boot.framework.util;


import cn.king.canal.client.spring.boot.framework.annotation.CanalTable;
import cn.king.canal.client.spring.boot.framework.enumerate.TableNameEnum;
import cn.king.canal.client.spring.boot.framework.handler.EntryHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerUtil {

    /**
     * @author:
     * @time: 2023-04-21 0:05
     * @param: entryHandlers
     * @param: tableName
     * @return: cn.king.canal.client.spring.boot.framework.handler.EntryHandler
     * @description: 根据 canalTableName 获取 EntryHandler 对象。
     */
    public static EntryHandler getEntryHandler(List<? extends EntryHandler> entryHandlers, String tableName) {
        EntryHandler globalHandler = null;
        for (EntryHandler handler : entryHandlers) {
            // 如果 canalTableName 为 all，那么就是全局的 EntryHandler 对象。
            String canalTableName = getCanalTableName(handler);
            if (TableNameEnum.ALL.name().toLowerCase().equals(canalTableName)) {
                globalHandler = handler;
                continue;
            }
            // 如果 canalTableName 与 tableName 相等，那么就是当前的 EntryHandler 对象。
            if (tableName.equals(canalTableName)) {
                return handler;
            }
        }
        return globalHandler;
    }

    /**
     * @author:
     * @time: 2023-04-21 0:04
     * @param: entryHandlers
     * @return: java.util.Map<java.lang.String, cn.king.canal.client.spring.boot.framework.handler.EntryHandler>
     * @description: 将 EntryHandler 列表转换为 Map, key 是 canalTableName, value 是 EntryHandler 对象。
     */
    public static Map<String, EntryHandler> getTableHandlerMap(List<? extends EntryHandler> entryHandlers) {
        Map<String, EntryHandler> map = new ConcurrentHashMap<>();
        if (entryHandlers != null && entryHandlers.size() > 0) {
            for (EntryHandler handler : entryHandlers) {
                String canalTableName = getCanalTableName(handler);
                if (canalTableName != null) {
                    map.putIfAbsent(canalTableName.toLowerCase(), handler);
                } else {
                }
            }
        }
        return map;
    }

    /**
     * @author:
     * @time: 2023-04-21 0:03
     * @param: map
     * @param: tableName
     * @return: cn.king.canal.client.spring.boot.framework.handler.EntryHandler
     * @description: 从 map 中获取对应的 EntryHandler 对象。
     * 输入是表名，输出是对应的 EntryHandler 对象。
     * 和 getCanalTableName 方法配合使用。
     */
    public static EntryHandler getEntryHandler(Map<String, EntryHandler> map, String tableName) {
        EntryHandler entryHandler = map.get(tableName);
        if (entryHandler == null) {
            return map.get(TableNameEnum.ALL.name().toLowerCase());
        }
        return entryHandler;
    }

    /**
     * @author:
     * @time: 2023-04-21 0:01
     * @param: entryHandler
     * @return: java.lang.String
     * @description: 获取 CanalTable 注解的 value 值。
     * <p>
     * 例如：
     * CanalTable(value = "t_user")
     * Component
     * public class UserHandler implements EntryHandler<User>{}
     * 输入 UserHandler 对象，输出 t_user。
     */
    public static String getCanalTableName(EntryHandler entryHandler) {
        CanalTable canalTable = entryHandler.getClass().getAnnotation(CanalTable.class);
        if (canalTable != null) {
            return canalTable.value();
        }
        return null;
    }

}
