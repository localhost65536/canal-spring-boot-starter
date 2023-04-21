package cn.king.canal.client.spring.boot.autoconfigure;

import cn.king.canal.client.spring.boot.framework.client.SimpleCanalClient;
import cn.king.canal.client.spring.boot.framework.factory.EntryColumnModelFactory;
import cn.king.canal.client.spring.boot.framework.handler.EntryHandler;
import cn.king.canal.client.spring.boot.framework.handler.MessageHandler;
import cn.king.canal.client.spring.boot.framework.handler.RowDataHandler;
import cn.king.canal.client.spring.boot.framework.handler.impl.AsyncMessageHandlerImpl;
import cn.king.canal.client.spring.boot.framework.handler.impl.RowDataHandlerImpl;
import cn.king.canal.client.spring.boot.framework.handler.impl.SyncMessageHandlerImpl;
import cn.king.canal.client.spring.boot.properties.CanalProperties;
import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Configuration
@EnableConfigurationProperties(CanalProperties.class)
@ConditionalOnBean(value = {EntryHandler.class})
@ConditionalOnProperty(value = "canal.mode", havingValue = "simple", matchIfMissing = true)
@Import(ThreadPoolAutoConfiguration.class)
public class SimpleClientAutoConfiguration {

    private final CanalProperties canalProperties; 

    public SimpleClientAutoConfiguration(CanalProperties canalProperties) {
        this.canalProperties = canalProperties;
    }
 
    @Bean
    public RowDataHandler<CanalEntry.RowData> rowDataHandler() {
        return new RowDataHandlerImpl(new EntryColumnModelFactory());
    }

    /**
     * @author:
     * @time: 2023-04-21 0:38
     * @param: rowDataHandler
     * @param: entryHandlers
     * @param: executorService
     * @return: cn.king.canal.client.spring.boot.framework.handler.MessageHandler
     * @description: 如果 CanalProperties.CANAL_ASYNC 属性的值为 "true" 或者未设置（即不存在），则才会创建当前的 Bean。
     * matchIfMissing 属性表示当该属性不存在时，是否默认视为匹配成功，默认值是 false。在本例中，我们将其设置为 true，表示如果没有设置该属性，则也认为该条件成立。
     */
    @Bean
    @ConditionalOnProperty(value = "canal.async", havingValue = "true", matchIfMissing = true)
    public MessageHandler asyncMessageHandler(RowDataHandler<CanalEntry.RowData> rowDataHandler, List<EntryHandler> entryHandlers,
                                         ExecutorService executorService) {
        return new AsyncMessageHandlerImpl(entryHandlers, rowDataHandler, executorService);
    }

    @Bean
    @ConditionalOnProperty(value = "canal.async", havingValue = "false")
    public MessageHandler syncMessageHandler(RowDataHandler<CanalEntry.RowData> rowDataHandler, List<EntryHandler> entryHandlers) {
        return new SyncMessageHandlerImpl(entryHandlers, rowDataHandler);
    }

    /**
     * @author:
     * @time: 2023-04-21 0:36
     * @param: messageHandler
     * @return: cn.king.canal.client.spring.boot.framework.client.SimpleCanalClient
     * @description: 创建canal连接
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public SimpleCanalClient simpleCanalClient(MessageHandler messageHandler) {
        String server = canalProperties.getServer();
        String[] array = server.split(":");
        return SimpleCanalClient.builder()
                .hostname(array[0])
                .port(Integer.parseInt(array[1]))
                .destination(canalProperties.getDestination())
                .userName(canalProperties.getUsername())
                .password(canalProperties.getPassword())
                .messageHandler(messageHandler)
                .batchSize(canalProperties.getBatchSize())
                .filter(canalProperties.getFilter())
                .timeout(canalProperties.getTimeout())
                .unit(canalProperties.getUnit())
                .build();
    }

}
