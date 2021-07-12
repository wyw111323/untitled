package com.tcsl.boot.netty.demo;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception {

        //创建一个ServerSocket
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8080));
        //设置为非阻塞模式
        serverChannel.configureBlocking(false);

        //创建一个事件查询器
        Selector selector = SelectorProvider.provider().openSelector();

        //把ServerSocketChannel注册到事件查询器上，并且关注OP_ACCEPT事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        //创建一组事件查询器
        EventLoopGroup eventLoopGroup = new EventLoopGroup();

        while (true) {
            //阻塞方法，等待系统有IO事件发生
            int eventNum = selector.select();
            System.out.println("系统发生IO事件 数量->" + eventNum);

            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterable = keySet.iterator();

            while(iterable.hasNext()) {
                SelectionKey key = iterable.next();
                iterable.remove();
                //连接事件
                if(key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    //接受客户端的连接,一个SocketChannel代表了一个TCP的连接
                    SocketChannel socketChannel = ssc.accept();
                    //把SocketChannel设置为异步模式
                    socketChannel.configureBlocking(false);
                    System.out.println("服务器接受了一个新的连接 " + socketChannel.getRemoteAddress());

                    //把SocketChannel注册到事件查询器上，并且关注OP_READ事件
                    //socketChannel.register(selector, SelectionKey.OP_READ);
                    eventLoopGroup.register(socketChannel, SelectionKey.OP_READ);
                }
            }
        }

    }
}
