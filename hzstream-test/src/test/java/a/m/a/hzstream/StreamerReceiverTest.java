package a.m.a.hzstream;

import a.m.a.hzstreamer.receiver.ResponseClient;
import a.m.a.hzstreamer.receiver.ResponseServer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class StreamerReceiverTest {

    private ExecutorService EXEC;

    @BeforeClass
    public void beforeClass() {
        EXEC = Executors.newCachedThreadPool();
    }

    @AfterClass
    public void afterClass() {
        EXEC.shutdown();
    }

    @Test
    public void response_client_should_open_connection_when_started_and_close_whe_terminated() throws Exception {
        // start server
        ResponseServer<Serializable> server = new ResponseServer<>(9999, data -> {
        });
        EXEC.execute(() -> {
            try {
                server.start();
            } catch (Exception e) {
                Assert.fail("Exception in server loop");
            }
        });

        // start client
        ResponseClient<Serializable> client = new ResponseClient<>("localhost", 9999);
        EXEC.execute(() -> {
            try {
                client.start();
            } catch (Exception e) {
                Assert.fail("Exception in client loop");
            }

        });
        ResponseClient<Serializable>.ActiveDataStreamer streamer = client.getActiveDataStreamer();

        // then
        Assert.assertNotNull(streamer);
        streamer.terminate();
    }

}
