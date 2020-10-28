
package netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * 功能简述：
 *
 * @author qcyki
 * @create 2020/10/28
 * @since 1.0.0
 */
public class HttpInitializer  extends ChannelInitializer<SocketChannel> {

    private SslContext sslCtx ;

    public HttpInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        if(sslCtx != null) {
            p.addLast(sslCtx.newHandler(socketChannel.alloc()));
        }
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpHandler());
    }
}
