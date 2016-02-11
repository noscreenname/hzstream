package a.m.a.hzstreamer.ex.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class DataPojo implements Serializable {

    public static final String MAP_NAME = "MAP-DATA-POJO";

    private final long id;
    private final String name;
    private final LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private final Object payload;

    public DataPojo(long id, String name, LocalDateTime createdOn, LocalDateTime updatedOn, Object payload) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.payload = payload;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public Object getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataPojo dataPojo = (DataPojo) o;

        if (id != dataPojo.id) return false;
        if (name != null ? !name.equals(dataPojo.name) : dataPojo.name != null) return false;
        if (createdOn != null ? !createdOn.equals(dataPojo.createdOn) : dataPojo.createdOn != null) return false;
        if (updatedOn != null ? !updatedOn.equals(dataPojo.updatedOn) : dataPojo.updatedOn != null) return false;
        return payload != null ? payload.equals(dataPojo.payload) : dataPojo.payload == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", payload=" + payload +
                '}';
    }
}
