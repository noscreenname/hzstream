package a.m.a.hzstreamer.receiver;

import java.io.*;

public final class DataMessage<T extends Serializable> {

    private final T data;

    public DataMessage(T data) {
        assert data != null;
        this.data = data;
    }

    public DataMessage(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        this.data = (T) ois.readObject();
    }

    // used for generics
    public static <T extends Serializable> T extractData(Object o) throws IOException, ClassNotFoundException {
        return new DataMessage<T>((byte[]) o).data;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bytes);
        oos.writeObject(data);
        oos.flush();
        return bytes.toByteArray();
    }
}
