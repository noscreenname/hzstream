package a.m.a.hzstreamer.ex.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;

public final class HzCluster {

    public static void main(String[] args) {
        int clusterSize = 2;
        // will add stuff here later
        Config config = new Config();
        // default ports are 5701, 5702...
        for (int i = 0; i < clusterSize; i++) {
            Hazelcast.newHazelcastInstance(config);
        }
    }
}
