package cn.king.canal.client.spring.boot.framework.handler;

/**
 * 消息处理器
 */
public interface MessageHandler<T> {
     
     void handleMessage(T t);
     
}
