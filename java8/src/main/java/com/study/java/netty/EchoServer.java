package com.study.java.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Jeffrey
 * @since 2017/11/25 13:47
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            // add handler to channel
            b.group(group).channel(NioServerSocketChannel.class).localAddress(port).
                option(ChannelOption.SO_BACKLOG, 100).childHandler(
                new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        // handler
                        channel.pipeline().addLast(new EchoServerHandler());
                    }
                });
            // bind server
            ChannelFuture future = b.bind().sync();
            System.out.println(
                EchoServer.class.getName() + " started and listen on " + future.channel()
                    .localAddress());
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(65535).start();
    }
}
