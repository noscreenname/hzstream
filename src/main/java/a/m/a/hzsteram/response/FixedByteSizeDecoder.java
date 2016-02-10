package a.m.a.hzsteram.response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public final class FixedByteSizeDecoder extends ByteToMessageDecoder {

    private final int size;

    public FixedByteSizeDecoder(int size) {
        this.size = size;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= size) {
            out.add(in.readableBytes());
        }
    }
}
