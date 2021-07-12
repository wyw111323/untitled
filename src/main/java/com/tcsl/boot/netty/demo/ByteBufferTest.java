package com.tcsl.boot.netty.demo;

import java.nio.ByteBuffer;

public class ByteBufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put((byte)23);
        buffer.putInt(888477777);
        buffer.flip();
        System.out.println(buffer.get());
        System.out.println(buffer.getInt());



        ByteBuffer directBuffer = ByteBuffer.allocateDirect(16);
        directBuffer.put((byte)23);
        directBuffer.get();
    }
}
