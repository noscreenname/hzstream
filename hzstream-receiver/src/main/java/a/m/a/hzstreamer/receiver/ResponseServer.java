package a.m.a.hzstreamer.receiver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

import java.io.Serializable;

public final class ResponseServer<T extends Serializable> {

    private static final Logger logger = Logger.getLogger(ResponseServer.class);

    private final int port;
    private final DataListener<T> listener;

    public ResponseServer(int port, DataListener<T> listener) {
        this.port = port;
        this.listener = listener;
    }

    public void start() throws Exception {
        logger.info("Starting response server on port " + port);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DataMessageDecoder(), new DataMessageHandler<>(listener));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections
            ChannelFuture f = b.bind(port).sync();

            // Wait util the server socket connections.
            f.channel().closeFuture().sync();
        } finally {
            logger.info("Closing response server on port " + port);
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
