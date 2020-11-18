package com.byz.demo.reactor.basic.io.service;

import com.byz.demo.reactor.socket.SocketUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 16:40
 */
public class Server {
    final int port;

    public Server(int port) {
        this.port = port;
    }

    void start() throws IOException {
        //启动服务器
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("启动服务");
        //监听连接
        while (true) {
            //获取连接
            Socket accept = serverSocket.accept();
            System.out.println("获取连接,IP:" + accept.getInetAddress().getHostAddress());
            //创建新线程处理请求
            new Thread(new EchoHandler(accept)).start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(SocketUtil.port).start();
    }
}
