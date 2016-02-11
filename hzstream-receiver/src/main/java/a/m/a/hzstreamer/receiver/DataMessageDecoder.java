package a.m.a.hzstreamer.receiver;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public final class DataMessageDecoder extends LengthFieldBasedFrameDecoder {

    public DataMessageDecoder() {
        //TODO AMA constants
        super(
                1024 * 1024, // maxFrameLength = 1MB
                0,           // no lengthFieldOffset
                2            // lengthFieldLength = 2 bytes (short)
        );
    }
}
