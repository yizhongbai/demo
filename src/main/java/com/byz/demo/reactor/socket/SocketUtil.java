package com.byz.demo.reactor.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-11-18 16:10
 */
public class SocketUtil {

    public static String ip = "127.0.0.1";
    public static int port = 5555;

    public static void read(Socket socket, BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        System.out.println("收到," + socket.getInetAddress().getHostAddress() + ",信息:" + line);
    }

    public static void write(Socket socket, BufferedWriter bufferedWriter, Scanner scanner) throws IOException {
        System.out.println("回复:");
        String next = scanner.next();
        System.out.println("发送," + socket.getInetAddress() + "信息：" + next);
        //"\n"为发送结束符，不然线程一直处于阻塞状态
        bufferedWriter.write(next + "\n");
        bufferedWriter.flush();
    }
}
