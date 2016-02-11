package a.m.a.hzstreamer.ex.feeder;

import a.m.a.hzstreamer.ex.data.DataPojo;
import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class DataGenerator {

    private final static Logger logger = Logger.getLogger(DataGenerator.class);

    private final int nameSize = 16;
    private final int payloadSize = 1024;
    private final Random random;

    DataGenerator() {
        random = new Random();
    }

    public Map<Long, DataPojo> generate(int nbData) {
        Stopwatch sw = Stopwatch.createStarted();
        Map<Long, DataPojo> dataPojos = new HashMap<>(nbData);
        for (int i = 0; i < nbData; i++) {
            LocalDateTime now = LocalDateTime.now();
            byte[] payload = new byte[payloadSize];
            random.nextBytes(new byte[payloadSize]);
            dataPojos.put((long) i,
                    new DataPojo(
                            i,
                            RandomStringUtils.randomAlphabetic(nameSize),
                            now,
                            now,
                            payload)
            );
        }
        int totalPayloadSizeMb = payloadSize * nbData / 1024 / 1024;
        logger.info(String.format("Generated %d random data pojos, total payload size = %,d MB.",
                nbData, totalPayloadSizeMb));
        return dataPojos;
    }
}
