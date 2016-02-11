package a.m.a.hzstreamer.ex.feeder;

import a.m.a.hzstreamer.ex.data.DataPojo;
import com.google.common.base.Stopwatch;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class HzFeeder {

    private final static Logger logger = Logger.getLogger(HzFeeder.class);

    public static void main(String[] args) {
        logger.info("### HzFeeder STARTED");

        //-- params
        int nbData = 10_000;
        String[] clusterAddress = {"localhost:5701", "localhost:5702"};

        //-- generate data
        logger.info("Generate data");
        Stopwatch stopwatch = Stopwatch.createStarted();
        DataGenerator dataGenerator = new DataGenerator();
        Map<Long, DataPojo> data = dataGenerator.generate(nbData);
        logger.info(String.format(">>> Data generation took %,d ms", stopwatch.elapsed(TimeUnit.MILLISECONDS)));

        //-- load data to hz
        logger.info("Connecting to Hazelcast using " + clusterAddress[0]);
        stopwatch.reset().start();
        ClientConfig config = new ClientConfig();
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        logger.info(String.format(">>> Cluster connection took %,d ms", stopwatch.elapsed(TimeUnit.MILLISECONDS)));

        logger.info("Load data to hazelcast cache");
        stopwatch.reset().start();
        IMap<Long, DataPojo> map = hz.getMap(DataPojo.MAP_NAME);
        map.putAll(data);
        logger.info(String.format(">>> Data insertion took %,d ms", stopwatch.elapsed(TimeUnit.MILLISECONDS)));

        logger.info("### HzFeeder STOPPED");
    }
}
