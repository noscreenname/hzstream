package a.m.a.hzstreamer.receiver;

import java.io.Serializable;

public interface DataStreamer<T extends Serializable> {

    void stream(T data);

    void terminate() throws InterruptedException;
}
