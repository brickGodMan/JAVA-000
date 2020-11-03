package nio02.gateway.outbound.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/3
 * @since 1.0.0
 */
public class NettyInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
        ch.pipeline().addLast(new HttpResponseDecoder());
        //客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
        ch.pipeline().addLast(new HttpRequestEncoder());
        ch.pipeline().addLast(new NettyClientHandler());
    }
}
