package com.study.java.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/24 17:09
 */
public class SocketDemo {

    private void bindPort(String host, int port) throws Exception {
        Socket s = new Socket();
        s.bind(new InetSocketAddress(host, port));
        s.close();
    }

    public boolean isPortAvailable(String host, int port) {
        Socket s = new Socket();
        try {
//            bindPort("0.0.0.0", port);
//            bindPort(InetAddress.getLocalHost().getHostAddress(), port);
            bindPort(host, port);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    public void test() {
        System.out.println(isPortAvailable("localhost", 8080));
        System.out.println(isPortAvailable("localhost", 8081));
        System.out.println(isPortAvailable("172.24.4.110", 22222));
        System.out.println(isPortAvailable("172.24.4.110", 8082));
        System.out.println(isPortAvailable("172.24.4.110", 111212));
    }

    @Test
    public void test1() {
        Socket s = new Socket();
        SocketAddress add = new InetSocketAddress("127.0.0.1", 8080);
        try {
            s.connect(add, 5000);
//            s.setKeepAlive(true);
//            s.setSoTimeout(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Socket s = new Socket();
        SocketAddress add = new InetSocketAddress("172.24.6.223", 20010);
        SocketAddress add1 = new InetSocketAddress("172.24.6.223", 30010);
        SocketAddress add2 = new InetSocketAddress("172.24.6.223", 40010);
        try {
            s.connect(add);
            s.connect(add1);
            s.connect(add2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
