package cn.king.canal.client.spring.boot.framework.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CanalModel {
    
    /**
     * 消息id
     */
    private long id;

    /**
     * 库名
     */
    private String database;

    /**
     * 表名
     */
    private String table;

    /**
     * binlog executeTime
     */
    private Long executeTime;

    /**
     * dml build timeStamp
     */
    private Long createTime;

}
