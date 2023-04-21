package cn.king.canal.client.spring.boot.properties;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@Data
@ConfigurationProperties(prefix = "canal")
public class CanalProperties {
    
    /**
     * 目前只支持 simple 模式，只能填写 simple
     */
    private String mode = "simple";

    private Boolean async;

    private String server;

    /**
     * canal 的 instance 名称 
     */
    private String destination;

    private String filter = StringUtils.EMPTY;

    private Integer batchSize = 1;

    private Long timeout = 1L;

    private TimeUnit unit = TimeUnit.SECONDS;

    private String username;

    private String password;

}
