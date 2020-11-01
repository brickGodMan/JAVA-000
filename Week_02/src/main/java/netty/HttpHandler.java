
package netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONNECTION;

/**
 * 功能简述：
 *
 * @author qcyki
 * @create 2020/10/28
 * @since 1.0.0
 */
public class HttpHandler  extends ChannelInboundHandlerAdapter {

    private static final String MARK_URI = "test";

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext)  {
        channelHandlerContext.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) {
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) o;
            String uri = fullHttpRequest.uri();
            if(uri.contains(MARK_URI)) {
                handlerTest(fullHttpRequest,channelHandlerContext);
            }
        } finally {
            ReferenceCountUtil.release(o);
        }
    }

    private void handlerTest(FullHttpRequest fullHttpRequest, ChannelHandlerContext channelHandlerContext) {
        FullHttpResponse response = null;
        try {
            String value = "hello,godman";
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type","application/json");
            response.headers().setInt("Content-Length",response.content().readableBytes());
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.NO_CONTENT);
        } finally {
            if(fullHttpRequest != null) {
                if(!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    channelHandlerContext.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION,KEEP_ALIVE);
                    channelHandlerContext.write(response);
                }
            }
        }
    }
}
