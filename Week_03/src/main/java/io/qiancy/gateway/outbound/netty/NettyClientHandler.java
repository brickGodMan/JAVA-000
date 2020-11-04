package io.qiancy.gateway.outbound.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/3
 * @since 1.0.0
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        FullHttpResponse response = null;
        System.out.println("================");

//        response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
//        response.headers().set("Content-Type", "application/json");
//
//        response.headers().setInt("Content-Length", Integer.parseInt(response1.headers("Content-Length").getValue()));
        if (msg instanceof HttpResponse) {
            HttpResponse response1 = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        if(msg instanceof HttpContent) {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(buf));
            System.out.println(buf.toString(CharsetUtil.UTF_8));
            buf.release();
        }
        if (!HttpUtil.isKeepAlive(response)) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.write(response);
        }
    }
}
