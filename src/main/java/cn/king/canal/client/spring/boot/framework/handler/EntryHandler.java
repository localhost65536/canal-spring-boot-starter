package cn.king.canal.client.spring.boot.framework.handler;

/**
 * 实体类处理器。
 */
public interface EntryHandler<T> {

    /**
     * @author:
     * @time: 2023-04-21 8:55
     * @param: t
     * @return: void
     * @description: 插入数据
     */
    default void insert(T t) {

    }

    /**
     * @author:
     * @time: 2023-04-21 8:55
     * @param: before
     * @param: after
     * @return: void
     * @description: 更新数据
     */
    default void update(T before, T after) {

    }

    /**
     * @author:
     * @time: 2023-04-21 8:55
     * @param: t
     * @return: void
     * @description: 删除数据
     */
    default void delete(T t) {

    }

}
