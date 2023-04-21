package cn.king.canal.client.spring.boot.framework.client;

import cn.king.canal.client.spring.boot.framework.handler.MessageHandler;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractCanalClient implements ICanalClient {

    /**
     * 控制线程启动停止的标志位。注意内存可见性volatile
     */
    protected volatile boolean flag;

    /**
     * 工作线程, 用于处理canal server推送过来的消息, 并将消息交给消息处理器处理。
     */
    private Thread workThread;

    /**
     * canal 连接器, 用于连接canal server。
     * <p>
     * 类似 java.sql.Connection。用于表示 Java 应用程序与数据库之间的连接。通过 Connection 对象，可以执行 SQL 语句、提交事务、管理连接池等操作。
     * 在 MySQL 数据库中，可以使用 com.mysql.jdbc.Driver 来获取 Connection 对象：Connection conn = DriverManager.getConnection(url, user, password);
     * <p>
     * 由于canal的原理是伪装成mysql的slave，因此可以将该类比为mysql的Connection。
     */
    @Getter
    @Setter
    private CanalConnector connector;

    /**
     * 用于覆盖canal server中配置的过滤规则。
     * <p>
     * 实际上就是指定需要监听binlog的数据库表，格式为：数据库名.表名。
     * 由于可以可以使用正则表达式的形式，因此该字段叫做filter。
     * <p>
     * 如果指定为空字符串，则使用canal server中配置的过滤规则。
     */
    @Setter
    @Getter
    protected String filter = StringUtils.EMPTY;

    /**
     * 下面3个配置项用于控制从canal server中获取消息的频率。
     * <p>
     * 由于canal server是以binlog为单位推送消息的，canal伪装成mysql的slave，因此这里的频率指的是每次从canal server中获取的消息数量。
     * <p>
     * batchSize: 一次获取的消息数量。
     * timeout: 一次获取消息的超时时间。
     * unit: 超时时间的单位。
     */
    @Setter
    @Getter
    protected Integer batchSize = 1;
    @Setter
    @Getter
    protected Long timeout = 1L;
    @Setter
    @Getter
    protected TimeUnit unit = TimeUnit.SECONDS;

    /**
     * 消息处理器。用于消费canal server推送过来的消息。
     */
    @Getter
    @Setter
    private MessageHandler messageHandler;

    /**
     * canal client bean 的 初始化方法
     */
    @Override
    public void init() {
        // 创建一个新的线程，并将该线程的任务指定为当前对象的process方法
        workThread = new Thread(this::process);
        workThread.setName("canal-client-thread");
        flag = true;
        workThread.start();
    }

    /**
     * canal client bean 的 销毁方法
     */
    @Override
    public void destroy() {
        flag = false;
        if (Objects.nonNull(workThread)) {
            workThread.interrupt();
        }
    }

    /**
     * 从canal中拉取消息并处理。
     * 原生客户端用法见：https://github.com/alibaba/canal/wiki/ClientAPI
     */
    @Override
    public void process() {
        while (flag) {
            try {
                // 连接canal server（理解成连接mysql）
                connector.connect();
                // 订阅指定的过滤规则（理解成要操作哪个库中的哪张表）
                connector.subscribe(filter);
                while (flag) {
                    // 获取指定数量的消息（理解成从mysql中获取指定数量的binlog 或 理解成从mysql中拉取指定数量的消息）
                    Message message = connector.getWithoutAck(batchSize, timeout, unit);
                    // 获取消息的id（理解成mq队列中的offset）
                    long batchId = message.getId();
                    log.info("获取消息 {}", message);
                    if (batchId != -1 && message.getEntries().size() != 0) {
                        messageHandler.handleMessage(message);
                    }
                    // 处理完消息，手动返回ack（理解成mysql中的binlog已经被消费了，可以删除了 或 理解成mq队列中的消息被消费了，可以返回offset了）
                    connector.ack(batchId);
                }
            } catch (Exception e) {
                log.error("canal client 异常", e);
            } finally {
                connector.disconnect();
            }
        }
    }
 
}
