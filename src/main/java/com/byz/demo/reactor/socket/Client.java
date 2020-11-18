package com.byz.demo.reactor.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 16:07
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
        System.out.println("开始连接服务器...");
        Socket socket = new Socket(ip, port);
        System.out.println("已连上服务器,IP:" + ip + ",端口:" + port);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //发送信息
            SocketUtil.write(socket, bufferedWriter, scanner);
            //读信息
            SocketUtil.read(socket, bufferedReader);
        }
    }

    public static void main(String[] args) throws IOException {
        new Client(SocketUtil.ip, SocketUtil.port).start();
    }
}
