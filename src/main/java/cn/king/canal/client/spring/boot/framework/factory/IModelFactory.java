package cn.king.canal.client.spring.boot.framework.factory;


import cn.king.canal.client.spring.boot.framework.handler.EntryHandler;

import java.util.Set;

/**
 * 模型工厂
 * <p>
 * 模型工厂用于将canal的数据转换为业务模型，业务模型可以是实体类，也可以是其他类型，比如Map，List等，
 * 具体取决于业务场景，但是必须是一个对象，不能是基本类型，比如int，long等。
 * <p>
 * 例如实现类 EntryColumnModelFactory 用于将canal的数据转换为EntryColumnModel对象，EntryColumnModel对象是一个Map，key为列名，value为列值。
 * <p>
 * 例如实现类 MapColumnModelFactory 用于将canal的数据转换为Map对象，Map对象的key为列名，value为列值。
 */
public interface IModelFactory<T> {

    <R> R newInstance(EntryHandler entryHandler, T t) throws Exception;

    default <R> R newInstance(EntryHandler entryHandler, T t, Set<String> updateColumn) throws Exception {
        return null;
    }

}
