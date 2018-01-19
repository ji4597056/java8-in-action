package com.study.java.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeffrey
 * @since 2017/06/01 9:49
 */
public class SocketTest {

    private static final Logger logger = LoggerFactory.getLogger(SocketTest.class);

    private static final String FILE_PATH = "C:\\Users\\jixiaogang\\Desktop\\=.=.txt";

    private static final String FILE_PATH_2 = "C:\\Users\\jixiaogang\\Desktop\\=.=.=.txt";

    private static final String FILE_PATH_3 = "F:\\书籍\\语言\\java\\Effective Java.pdf";

    private static final Integer SERVER_SOCKET_PORT = 9999;

    private static final Integer SERVER_SELECT_TIMEOUT = 3000;

    @Test
    public void test1() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(FILE_PATH, "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        while (channel.read(byteBuffer) != -1) {    //read into buffer
            byteBuffer.flip();  //make buffer ready for read
            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.clear();
        }
        randomAccessFile.close();
    }

    @Test
    public void test2() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile(FILE_PATH, "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile(FILE_PATH_2, "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);   //将数据从源通道传输到FileChannel中
        fromFile.close();
        toFile.close();
    }

    @Test
    public void test3() throws IOException {
        RandomAccessFile toFile = new RandomAccessFile(FILE_PATH_2, "rw");
        FileChannel toChannel = toFile.getChannel();
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        buffer.put(newData.getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            toChannel.write(buffer);
        }
        toChannel.close();
        toFile.close();
    }

    @Test
    public void test4() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(SERVER_SOCKET_PORT));
        serverSocketChannel.configureBlocking(false);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(48);
                byteBuffer.put("嘿嘿嘿".getBytes());
                System.out.println(
                    "hi,I am from " + socketChannel.socket().getInetAddress().toString() + ":"
                        + socketChannel.socket().getPort());
                socketChannel.write(byteBuffer);
                socketChannel.close();
            }
        }
    }

    /**
     * socket client
     */
    @Test
    public void test5() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", SERVER_SOCKET_PORT));
        socketChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int count = 1;
        while (socketChannel.isConnected()) {
            byteBuffer.clear();
            if (socketChannel.read(byteBuffer) > 0) {
                logger.info("Receive server message:" + new String(byteBuffer.array()));
            }
            Thread.sleep(500L);
            byteBuffer.clear();
            byteBuffer.put(
                (System.currentTimeMillis() + ",count:" + count + ",hi,I am " + socketChannel
                    .socket().getInetAddress()
                    .toString() + ":"
                    + socketChannel.socket().getPort()).getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            if (count++ == 10) {
                socketChannel.close();
                break;
            }
        }
    }

    /**
     * socket server
     */
    @Test
    public void test6() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);   //设置非阻塞
        serverSocketChannel.bind(new InetSocketAddress("localhost", SERVER_SOCKET_PORT));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 等待某信道就绪(或超时)
            if (selector.select(SERVER_SELECT_TIMEOUT) == 0) {// 监听注册通道，当其中有注册的 IO
                // 操作可以进行时，该函数返回，并将对应的
                // SelectionKey 加入 selected-key
                logger.info("独自等待.");
                continue;
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            keys.forEach(key -> {
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key
                            .channel();
                        SocketChannel socketChannel = acceptServerSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        logger.info("Accpet request from {}", socketChannel.getRemoteAddress());
                        socketChannel
                            .register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        buffer.clear();
                        if (socketChannel.read(buffer) <= 0) {
                            socketChannel.close();
                            key.cancel();
                            logger.info("Received invalide data, close the connection");
                            return;
                        }
                        logger.info("Received message:{}", new String(buffer.array()));
                        buffer.clear();
                        buffer.put("Server has received.".getBytes());
                        socketChannel.write(buffer);
                    }
                } catch (IOException e) {
                    key.cancel();
                    logger.error("socket error:", e.getMessage());
                } finally {
                    keys.remove(key);
                }
            });
        }
    }

    @Test
    public void test7() throws IOException {
        FileInputStream fis = new FileInputStream(new File(FILE_PATH));
        FileOutputStream fos = new FileOutputStream(new File(FILE_PATH_2));
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) >= 0) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
        inChannel.close();
        outChannel.close();
        fis.close();
        fos.close();
    }

    @Test
    public void test8() throws IOException {
        long time = System.currentTimeMillis();
        RandomAccessFile file = new RandomAccessFile(FILE_PATH_3, "rw");
        RandomAccessFile toFile = new RandomAccessFile(FILE_PATH_2, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
        byteBuffer.clear();
        channel.read(byteBuffer);
        byteBuffer.flip();
        toFile.getChannel().write(byteBuffer);
        logger.info("cost time: {} ms", System.currentTimeMillis() - time);
        channel.close();
        toFile.close();
        file.close();
    }

    @Test
    public void test9() throws IOException {
        long time = System.currentTimeMillis();
        RandomAccessFile file = new RandomAccessFile(FILE_PATH_3, "rw");
        RandomAccessFile toFile = new RandomAccessFile(FILE_PATH_2, "rw");
        FileChannel channel = file.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(MapMode.READ_ONLY, 0, channel.size());
        toFile.getChannel().write(mappedByteBuffer);
        logger.info("cost time: {} ms", System.currentTimeMillis() - time);
        channel.close();
        toFile.close();
        file.close();
    }

    @Test
    public void test11() throws IOException {
        long time = System.currentTimeMillis();
        Path fromPath = Paths.get(FILE_PATH_3);
        Path toPath = Paths.get(FILE_PATH_2);
        Files.copy(fromPath, toPath);
        logger.info("cost time: {} ms", System.currentTimeMillis() - time);
    }

    @Test
    public void test10() {
        String str = "This is a string that Java natively stores as Unicode.";
        Charset charset = Charset.defaultCharset();
        ByteBuffer bbuf = charset.encode(CharBuffer.wrap(str));
        System.out.println(new String(bbuf.array()));
    }
}
