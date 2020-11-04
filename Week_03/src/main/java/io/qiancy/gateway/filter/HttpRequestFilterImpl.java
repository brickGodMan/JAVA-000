package io.qiancy.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/3
 * @since 1.0.0
 */
public class HttpRequestFilterImpl implements  HttpRequestFilter{

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String key = "nio";
        String value = "qiancy";
        fullRequest.headers().add(key,value);
    }
}
