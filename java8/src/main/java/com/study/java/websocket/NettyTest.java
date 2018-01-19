package com.study.java.websocket;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeffrey
 * @since 2017/11/23 14:05
 */
public class NettyTest {

    private static final Logger logger = LoggerFactory.getLogger(NettyTest.class);
    public static String HOST = "172.24.4.71";
    public static int PORT = 2222;

    public static Bootstrap bootstrap = getBootstrap();
    public static Channel channel = getChannel(HOST, PORT);

    public boolean startTcp = false;

    /**
     * 初始化Bootstrap
     */
    public static final Bootstrap getBootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

//                pipeline.addLast("framer",
//                    new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//                pipeline.addLast("frameDecoder",
//                    new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
//                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast("handler", new TcpClientHandler());
            }
        });
//        b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    public static final Channel getChannel(String host, int port) {
        Channel channel = null;
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            channel = future.channel();
        } catch (Exception e) {
            logger.error(
                String.format("连接Server(IP[%s],PORT[%s])失败", host, port), e);
            return null;
        }
        return channel;
    }

    public static void sendMsg(String msg) throws Exception {
        if (channel != null) {
            channel.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        } else {
            logger.warn("消息发送失败,连接尚未建立!");
        }
    }

    public static void sendConnectMsg(Channel channel) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("Detach", false);
        map.put("Tty", true);
        channel.writeAndFlush(
            Unpooled.copiedBuffer(wrapMsg(JSON.toJSONString(map)), CharsetUtil.UTF_8));
    }

    public static String wrapMsg(String msg) {
        String execId = "d0d012e86a9c8c06316aeb865ac60961b653048183a07f24933c80cc1dfa4764";
        StringBuilder builder = new StringBuilder();
        builder.append("POST /exec/").append(execId).append("/start HTTP/1.1\r\n");
        builder.append("Host: ").append(HOST).append(":").append(PORT).append("\r\n");
        builder.append("Connection: Upgrade\r\n");
        builder.append("Content-Type: application/json\r\n");
        builder.append("Upgrade: tcp\r\n");
        builder.append("Content-Length: ").append(msg.length()).append("\r\n");
        builder.append("\r\n");
        builder.append(msg);
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        try {
            long t0 = System.nanoTime();
            for (int i = 0; i < 1000; i++) {
                NettyTest.sendMsg("ls\r\n");
                TimeUnit.SECONDS.sleep(3L);
            }
            long t1 = System.nanoTime();
            System.out.println((t1 - t0) / 1000000.0);
            channel.closeFuture().sync();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static class TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

        private static final Logger logger = LoggerFactory.getLogger(TcpClientHandler.class);

        private boolean startTcp = false;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("connection finished");
            sendConnectMsg(ctx.channel());
            super.channelActive(ctx);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
            throws Exception {
            if (startTcp) {
                logger
                    .info("client receive:" + new String(ByteBufUtil.getBytes(msg),
                        CharsetUtil.UTF_8));
            } else {
                if (msg.toString(CharsetUtil.UTF_8).endsWith("\r\n")) {
                    startTcp = true;
                }
            }
//            ctx.writeAndFlush(Unpooled.copiedBuffer("ls\r\n".getBytes(CharsetUtil.UTF_8)));
            // messageReceived方法,名称很别扭，像是一个内部方法.
//            byte[] bytes = Base64.encodeBase64(ByteBufUtil.getBytes(msg));
//            String encode = new String(bytes);
//            logger.info("client encode:" + encode);
//            String decode = new String(Base64.decodeBase64(bytes));
//            logger.info("client decode:" + decode);
//            logger.info("client:" + msg.toString(CharsetUtil.UTF_8));
        }
    }
}
