package cn.king.canal.client.spring.boot.framework.handler.impl;


import cn.king.canal.client.spring.boot.framework.handler.AbstractMessageHandler;
import cn.king.canal.client.spring.boot.framework.handler.EntryHandler;
import cn.king.canal.client.spring.boot.framework.handler.RowDataHandler;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class AsyncMessageHandlerImpl extends AbstractMessageHandler {

    private ExecutorService executor;

    /**
     * @author:
     * @time: 2023-04-21 14:21
     * @param: entryHandlers 
     * @param: rowDataHandler 
     * @param: executor
     * @return:
     * @description:
     */
    public AsyncMessageHandlerImpl(List<? extends EntryHandler> entryHandlers,
                                   RowDataHandler<CanalEntry.RowData> rowDataHandler,
                                   ExecutorService executor) {
        super(entryHandlers, rowDataHandler);
        this.executor = executor;
    }

    @Override
    public void handleMessage(Message message) {
        executor.execute(() -> super.handleMessage(message));
    }

}
