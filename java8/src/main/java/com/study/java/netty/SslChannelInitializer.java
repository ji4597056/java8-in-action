package com.study.java.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * @author Jeffrey
 * @since 2017/11/26 17:37
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SSLContext context;

    private final boolean client;

    private final boolean startTls;

    public SslChannelInitializer(SSLContext context, boolean client, boolean startTls) {
        this.context = context;
        this.client = client;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(client);
        channel.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
    }
}
