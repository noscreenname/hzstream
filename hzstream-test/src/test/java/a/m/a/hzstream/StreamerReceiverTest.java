package a.m.a.hzstream;

import a.m.a.hzstreamer.receiver.ResponseClient;
import a.m.a.hzstreamer.receiver.ResponseServer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.Serializable;

public final class StreamerReceiverTest {

    @Test
    public void when_receiver_is_ready_streamer_should_activate() throws Exception {
        //-- GIVEN
        ResponseServer<Serializable> server = new ResponseServer<>(9999, data -> {
        });
        server.run();

        //-- WHEN
        ResponseClient<Serializable> client = new ResponseClient<>("localhost", 9999);
        ResponseClient<Serializable>.ActiveDataStreamer streamer = client.activate();

        //-- THEN
        Assert.assertNotNull(streamer);
        streamer.terminate();
    }
}
