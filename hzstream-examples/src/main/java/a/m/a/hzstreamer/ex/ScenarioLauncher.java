package a.m.a.hzstreamer.ex;

import a.m.a.hzstreamer.ex.consumer.HzConsumer;
import a.m.a.hzstreamer.ex.feeder.HzFeeder;
import a.m.a.hzstreamer.ex.hazelcast.HzCluster;

public final class ScenarioLauncher {

    public static void main(String[] args) {
        //-- start cluster
        HzCluster.main(args);
        //-- load data
        HzFeeder.main(args);
        //-- fetch data
        HzConsumer.main(args);
    }
}
