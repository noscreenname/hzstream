package a.m.a.hzstreamer.ex.hazelcast;

import a.m.a.hzstreamer.ex.data.DataPojo;
import com.hazelcast.config.Config;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.core.Hazelcast;

public final class HzCluster {

    public static void main(String[] args) {
        int clusterSize = 2;
        // use default config, overriding just what is needed
        Config config = new Config();
        config.getMapConfig(DataPojo.MAP_NAME).setInMemoryFormat(InMemoryFormat.BINARY)
//        config.getMapConfig(DataPojo.MAP_NAME).setInMemoryFormat(InMemoryFormat.OBJECT)
                .setBackupCount(1);
        // default ports are 5701, 5702...
        for (int i = 0; i < clusterSize; i++) {
            Hazelcast.newHazelcastInstance(config);
        }
    }
}
