package com.byz.demo.reactor.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 16:06
 */
public class Server {

    final int port;

    public Server(int port) {
        this.port = port;
    }

    void start() throws IOException {
        System.out.println("服务器已启动，端口号:" + port);
        //启动服务器
        ServerSocket serverSocket = new ServerSocket(port);
        Socket accept = serverSocket.accept();
        System.out.println("接收到请求,IP:" + accept.getInetAddress().getHostAddress());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //读
            SocketUtil.read(accept, bufferedReader);
            //写
            SocketUtil.write(accept, bufferedWriter, scanner);
        }
        //读信息
        //发信息
    }

    public static void main(String[] args) throws IOException {
        //1个服务端，1个客户端
        new Server(SocketUtil.port).start();
    }
}
