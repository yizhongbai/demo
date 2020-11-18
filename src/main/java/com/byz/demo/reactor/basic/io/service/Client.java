package com.byz.demo.reactor.basic.io.service;

import com.byz.demo.reactor.socket.SocketUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 16:40
 */
public class Client {
    final String ip;
    final int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    void start() throws IOException {
        //连接
        Socket socket = new Socket(ip, port);
        //资源准备
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //写
            SocketUtil.write(socket, bufferedWriter, scanner);
            //读
            SocketUtil.read(socket, bufferedReader);
        }
    }

    public static void main(String[] args) throws IOException {
        new Client(SocketUtil.ip, SocketUtil.port).start();
    }
}
