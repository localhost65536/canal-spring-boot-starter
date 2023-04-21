package cn.king.canal.client.spring.boot.framework.handler.impl;

import cn.king.canal.client.spring.boot.framework.handler.AbstractMessageHandler;
import cn.king.canal.client.spring.boot.framework.handler.EntryHandler;
import cn.king.canal.client.spring.boot.framework.handler.RowDataHandler;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;

import java.util.List;

public class SyncMessageHandlerImpl extends AbstractMessageHandler {

    public SyncMessageHandlerImpl(List<? extends EntryHandler> entryHandlers, RowDataHandler<CanalEntry.RowData> rowDataHandler) {
        super(entryHandlers, rowDataHandler);
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
    }

}
