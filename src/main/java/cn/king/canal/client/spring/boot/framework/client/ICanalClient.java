package cn.king.canal.client.spring.boot.framework.client;

public interface ICanalClient {

    /**
     * 客户端的初始化方法
     */
    void init();

    /**
     * 客户端的销毁方法
     */
    void destroy();

    /**
     * 客户端的处理方法
     */
    void process();

}
