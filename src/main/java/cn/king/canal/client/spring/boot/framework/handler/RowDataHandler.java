package cn.king.canal.client.spring.boot.framework.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 处理一行数据
 */
public interface RowDataHandler<T> {

    /**
     * @author:
     * @time: 2023-04-21 9:13
     * @param: t
     * @param: entryHandler
     * @param: eventType
     * @return: void
     * @description: 处理一行数据。使用 entryHandler（处理器） 根据 eventType（增删改的类型） 处理 t（ CanalEntry.RowData 一行数据）。
     * <p>
     * `com.alibaba.otter.canal.protocol.CanalEntry` 是 Canal 协议中定义的一个 Java 类，用于表示从 MySQL 数据库中获取到的数据变更事件，具体包含了以下内容：
     * <p>
     * `CanalEntry.Entry`：每个 Entry 包含了一个属于某个数据库实例（instance）下的一个特定表（table）的一次操作。可以认为是一个 DML 语句（例如：insert、update 和 delete）构成的逻辑单元。
     * 理解成 CanalEntry.Entry 封装了 binlog 日志中的一条记录。
     * <p>
     * `CanalEntry.RowChange`：RowChange 对象用于描述对表中行数据的一次修改操作，它包含"insert/update/delete"三种修改类型之一、数据库名、表名以及发生修改的所有行数据等信息。
     * <p>
     * `CanalEntry.Column`：Column 对象表示表中的一列数据，包含列名和列值两个属性。
     * <p>
     * `CanalEntry.EventType`：EventType 枚举表示 RowChange 中可能出现的操作类型，包括 INSERT、UPDATE 和 DELETE 等。
     * <p>
     * `CanalEntry.RowData`：RowData 对象代表一行数据，包含多个 Column（列） 对象组成的列表。
     * <p>
     * 总的来说，`com.alibaba.otter.canal.protocol.CanalEntry` 提供了一种类似于 ORM 框架的方式，将数据库中的数据变更事件抽象为一组对象，并提供了相应的方法和属性来方便地处理这些事件。该类在 Canal 中被广泛使用，在数据同步和数据分析等场景中发挥着重要作用。
     */
    <R> void handlerRowData(T t, EntryHandler<R> entryHandler, CanalEntry.EventType eventType) throws Exception;


}
