package a.m.a.hzsteram.response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public final class ResponseClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        try {
            long currentTime = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTime));
            ctx.close();
        } finally {
            m.release();
        }
    }
}
