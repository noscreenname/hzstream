package a.m.a.hzstreamer.receiver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.Serializable;

public final class DataMessageHandler<T extends Serializable> extends ChannelInboundHandlerAdapter {

    private final DataListener<T> listener;

    public DataMessageHandler(DataListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        listener.onDataReceived(DataMessage.<T>extractData(msg));
    }
}
