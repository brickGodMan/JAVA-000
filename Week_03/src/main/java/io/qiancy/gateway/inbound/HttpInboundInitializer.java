package io.qiancy.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 功能简述：初始化channal
 *
 * @author qiancy
 * @create 2020/11/2
 * @since 1.0.0
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private String proxyServer;

    public HttpInboundInitializer(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        //http编码
        p.addLast(new HttpServerCodec());
        //把请求与响应转变为单个FullHttpRequest或者FullHttpResponse
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        //加入handler
        p.addLast(new HttpInboundHandler(this.proxyServer));
    }
}
