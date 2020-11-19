package com.byz.demo.reactor.basic.reactor.design;

import com.byz.demo.reactor.socket.SocketUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 18:34
 */
public class Client {

    final String ip;
    final int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    void start() throws IOException {
        //连接服务器
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(ip, port));
        socketChannel.configureBlocking(false);
        //等待连接服务器成功
        while (!socketChannel.finishConnect()) {

        }
        System.out.println("已连接服务");
        //选择器
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()) {
                SelectionKey sk = it.next();
                if (sk.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = socketChannel.read(byteBuffer);
                    //设置成可读
                    byteBuffer.flip();
                    String str = new String(byteBuffer.array(), 0, read);
                    System.out.println("接收到信息：" + str);
                }

                if (sk.isWritable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    System.out.println("请输入信息：");
                    Scanner scanner = new Scanner(System.in);
                    String next = scanner.next();
                    byteBuffer.put(next.getBytes(StandardCharsets.UTF_8));
                    //设置成可读
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    //设置成可写
                    byteBuffer.clear();
                    System.out.println("已发送信息:" + next);
                }
            }
            selectionKeys.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        new Client(SocketUtil.ip, SocketUtil.port).start();
    }
}
