package io.qiancy.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.qiancy.gateway.filter.HttpRequestFilterImpl;
import io.qiancy.gateway.outbound.httpclient.HttpOutboundHandler;
import io.qiancy.gateway.outbound.netty.NettyClient;

/**
 * 功能简述：入站处理
 *
 * @author qiancy
 * @create 2020/11/2
 * @since 1.0.0
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private final String proxyServer;
    private HttpOutboundHandler handler;
    private NettyClient client;

    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        handler = new HttpOutboundHandler(this.proxyServer);
        client = new NettyClient();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        new HttpRequestFilterImpl().filter(fullRequest,ctx);
        handler.handle(fullRequest, ctx);
    }
}
