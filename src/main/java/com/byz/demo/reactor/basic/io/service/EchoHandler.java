package com.byz.demo.reactor.basic.io.service;

import com.byz.demo.reactor.socket.SocketUtil;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 16:40
 */
public class EchoHandler implements Runnable {

    final Socket socket;

    public EchoHandler(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            //读
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);
            //读
            SocketUtil.read(socket, bufferedReader);
            //写
            SocketUtil.write(socket, bufferedWriter, scanner);
        }
    }
}
