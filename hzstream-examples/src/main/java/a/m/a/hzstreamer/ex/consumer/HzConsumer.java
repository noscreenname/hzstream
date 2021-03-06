package a.m.a.hzstreamer.ex.consumer;


import a.m.a.hzstreamer.ex.data.DataPojo;
import com.google.common.base.Stopwatch;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.query.Predicate;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public final class HzConsumer {

    public static final Logger logger = Logger.getLogger(HzConsumer.class);

    public static void main(String[] args) {
        logger.info("### HzConsumer STARTED");
        HazelcastInstance hz = null;

        try {
            //--params
            String[] clusterAddress = {"localhost:5701", "localhost:5702"};

            //-- connect to cluster
            Stopwatch stopwatch = Stopwatch.createStarted();
            logger.info("Connecting to Hazelcast using " + clusterAddress[0]);
            ClientConfig config = new ClientConfig();
            hz = HazelcastClient.newHazelcastClient(config);
            logger.info(String.format(">>> Cluster connection took %,d ms", stopwatch.elapsed(TimeUnit.MILLISECONDS)));

            //-- fetch data
            logger.info("Fetching ALL data from cluster");
            stopwatch.reset().start();
            Collection<DataPojo> dataPojos = fetchAll(hz);
            logger.info(String.format(">>> Retrieved %,d pojos, took %,d ms",
                    dataPojos.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));

            logger.info("Fetching \"name contains 'a'\" from cluster");
            stopwatch.reset().start();
            dataPojos.clear();
            dataPojos = fetchPredicate(hz, entry -> entry.getValue().getName().contains("a"));
            logger.info(String.format(">>> Retrieved %,d pojos, took %,d ms",
                    dataPojos.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        } finally {
            if (hz != null) {
                hz.shutdown();
            }
            logger.info("### HzConsumer STOPPED");
        }
        System.exit(0);
    }

    private static Collection<DataPojo> fetchAll(HazelcastInstance hz) {
        return hz.<Long, DataPojo>getMap(DataPojo.MAP_NAME).values();
    }

    private static Collection<DataPojo> fetchPredicate(HazelcastInstance hz, Predicate<Long, DataPojo> predicate) {
        return hz.<Long, DataPojo>getMap(DataPojo.MAP_NAME).values(predicate);
    }
}
