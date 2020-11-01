
package nio01.netty;

/**
 * 功能简述：
 *
 * @author qcyki
 * @create 2020/10/28
 * @since 1.0.0
 */
public class NettyServer {
    public static void main(String[] args) {
        try {
            new HttpServer(false,8808).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
