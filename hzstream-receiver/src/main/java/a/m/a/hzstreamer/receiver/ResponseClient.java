package a.m.a.hzstreamer.receiver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ResponseClient<T extends Serializable> {

    private static final Logger logger = Logger.getLogger(ResponseServer.class);

    public static final long ACTIVATION_TIMEOUT_MS = 2_000;

    private final String host;
    private final int port;
    private final CountDownLatch activated = new CountDownLatch(1);
    private final CountDownLatch terminated = new CountDownLatch(1);
    private AtomicReference<Channel> channelRef;

    public ResponseClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.channelRef = new AtomicReference<>(null);
    }

    public void start() throws Exception {
        logger.info("Starting response client : connecting to " + host + ":" + port);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // setup client loop
            Bootstrap b = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ResponseClientHandler());

            // connect to response server and return active streamer
            ChannelFuture f = b.connect(host, port).sync();
            channelRef.set(f.channel());
            activated.countDown();

            // wait until all data is sent to close connection
            terminated.await();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            channelRef.set(null);
            logger.info("Response client has terminated");
        }
    }

    //TODO add timeout param
    @Nullable
    public ActiveDataStreamer getActiveDataStreamer() throws InterruptedException {
        //TODO would be cleaner with a singleton in case of multiple calls,
        // but since we are wrapping the same channel, its not so bad
        ActiveDataStreamer streamer = null;
        boolean isActivated = activated.await(ACTIVATION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        Channel channel = channelRef.get();
        if (isActivated && channel != null) {
            streamer = new ActiveDataStreamer(channel);
        } else {
            logger.error("Failed to initialize channel");
        }
        return streamer;
    }

    public final class ActiveDataStreamer implements DataStreamer<T> {

        private final Channel channel;

        private ActiveDataStreamer(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void stream(T data) {
            //TODO disable streaming after terminated or if connection is lost
            channel.write(new DataMessage<>(data));
        }

        @Override
        public void terminate() throws InterruptedException {
            //TODO send endofdata message
            terminated.countDown();
        }
    }

}
