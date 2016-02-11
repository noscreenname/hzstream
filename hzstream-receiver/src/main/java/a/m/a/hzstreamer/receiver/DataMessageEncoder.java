package a.m.a.hzstreamer.receiver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.Serializable;

//TODO AMA figure out generics
public final class DataMessageEncoder<T extends Serializable> extends MessageToByteEncoder<DataMessage<T>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, DataMessage<T> msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.toBytes());
    }
}
