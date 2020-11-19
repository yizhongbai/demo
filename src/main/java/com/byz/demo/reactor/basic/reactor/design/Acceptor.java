package com.byz.demo.reactor.basic.reactor.design;

import lombok.SneakyThrows;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 17:52
 */
public class Acceptor implements Runnable {
    private ServerSocketChannel channel;
    private Selector selector;

    public Acceptor(ServerSocketChannel channel, Selector selector) {
        this.channel = channel;
        this.selector = selector;
    }

    @SneakyThrows
    @Override
    public void run() {
        SocketChannel socketChannel = channel.accept();
        if (socketChannel != null) {
            new Handler(socketChannel, selector);
        }
    }
}
