package com.byz.demo.reactor.basic.reactor.design;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 17:51
 */
public class Handler implements Runnable {

    private SocketChannel channel;
    private Selector selector;
    SelectionKey sk;
    static final int RECIEVING = 0, SENDING = 1;
    int state = RECIEVING;
    String msg;


    public Handler(SocketChannel channel, Selector selector) throws IOException {
        this.channel = channel;
        this.selector = selector;
        this.init();
    }

    void init() throws IOException {
        channel.configureBlocking(false);
        sk = channel.register(selector, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == SENDING) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
                //发送信息
                //设置成可读
                byteBuffer.flip();
                channel.write(byteBuffer);
                System.out.println("发送消息:" + msg);
                //写完后,注册read就绪事件
                sk.interestOps(SelectionKey.OP_READ);
                //写完后,进入接收的状态
                state = RECIEVING;
            } else if (state == RECIEVING) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                //读写信息
                //从通道读
                int length = 0;
                while ((length = channel.read(byteBuffer)) > 0) {
                    msg = new String(byteBuffer.array(), 0, length);
                    System.out.println("收到信息:" + msg);
                }
                //读完后，注册write就绪事件
                sk.interestOps(SelectionKey.OP_WRITE);
                //读完后,进入发送的状态
                state = SENDING;
            }
            //处理结束了, 这里不能关闭select key，需要重复使用
            //sk.cancel();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
