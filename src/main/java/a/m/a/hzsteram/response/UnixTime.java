package a.m.a.hzsteram.response;

import java.util.Date;

public final class UnixTime {

    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 100L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value - 2208988800L) * 1000L).toString();
    }
}
