package a.m.a.hzstreamer.receiver;

public interface DataListener<T> {

    void onDataReceived(T data);
}
