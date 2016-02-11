package a.m.a.hzstreamer.receiver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.Serializable;

public final class ResponseClient<T extends Serializable> {

    private final String host;
    private final int port;

    public ResponseClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ActiveDataStreamer activate() throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true);

            // connect to response server and return active streamer
            ChannelFuture f = b.connect(host, port).sync();
            return new ActiveDataStreamer(f.channel());
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public class ActiveDataStreamer implements DataStreamer<T> {

        private final Channel channel;

        public ActiveDataStreamer(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void stream(T data) {
            assert data != null;
            channel.write(new DataMessage<>(data));
        }

        @Override
        public void terminate() throws InterruptedException {
            channel.closeFuture().sync();
        }
    }
}
