package a.m.a.hzstreamer.ex.consumer;


import a.m.a.hzstreamer.ex.data.DataPojo;
import com.google.common.base.Stopwatch;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public final class HzConsumer {

    public static final Logger logger = Logger.getLogger(HzConsumer.class);

    public static void main(String[] args) {
        logger.info("### HzConsumer STARTED");

        //--params
        String[] clusterAddress = {"localhost:5701", "localhost:5702"};

        //-- connect to cluster
        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.info("Connecting to Hazelcast using " + clusterAddress[0]);
        ClientConfig config = new ClientConfig();
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        logger.info(String.format(">>> Cluster connection took %,d ms", stopwatch.elapsed(TimeUnit.MILLISECONDS)));

        //-- fetch data
        logger.info("Fetching data from cluster");
        stopwatch.reset().start();
        Collection<DataPojo> dataPojos = hz.<Long, DataPojo>getMap(DataPojo.MAP_NAME).values();
        logger.info(String.format(">>> Retrieved %,d pojos, took %,d ms",
                dataPojos.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));

        logger.info("### HzConsumer STOPPED");
    }
}
