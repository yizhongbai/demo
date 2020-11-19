package com.byz.demo.reactor.basic.reactor.design;

import com.byz.demo.reactor.socket.SocketUtil;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 17:51
 */
public class Reactor implements Runnable {

    final int port;

    Selector selector;

    public Reactor(int port) throws IOException {
        this.port = port;
        this.init();
    }

    void init() throws IOException {
        //创建服务
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        //绑定事件
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //Accept SelectionKey绑定Acceptor
        sk.attach(new Acceptor(serverSocketChannel, selector));
    }


    @SneakyThrows
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                dispatch(iterator.next());
            }
            selectionKeys.clear();
        }
    }

    private void dispatch(SelectionKey next) {
        Runnable r = (Runnable) next.attachment();
        if (r != null) {
            r.run();
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(SocketUtil.port)).start();
    }
}
