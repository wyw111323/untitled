package com.tcsl.boot.netty.demo;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class ServerSocketChannelDemo {

    @Test
    public void testServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置异步
        serverSocketChannel.configureBlocking(false);
        // 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8088));

        // 创建Selector
        Selector selector = SelectorProvider.provider().openSelector();

        // 注册监听事件  ServerSocketChannel 只接受 OP_ACCEPT 事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 刷新键
            int count = selector.select();
            if (count > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    // 连接事件
                    if (key.isAcceptable()) {
                        System.out.println("服务器接收一个连接请求... ");

                        ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
                        // 获取一个连接
                        SocketChannel accept = socketChannel.accept();
                        // 设置异步
                        accept.configureBlocking(false);

                        accept.register(selector,SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        System.out.println("处理读请求.....~~~");
                        SocketChannel channel = ((SocketChannel) key.channel());
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while (channel.read(byteBuffer) == -1) {
                            System.out.println("流关闭....");
                            channel.close();
                        }
                        byteBuffer.flip();
                        System.out.println(byteBuffer);;
                    }
                }
            }
        }

    }
}
