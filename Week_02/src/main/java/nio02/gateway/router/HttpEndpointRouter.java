package nio02.gateway.router;

import java.util.List;

/**
 * 功能简述：路由分配到不同的服务
 *
 * @author qiancy
 * @create 2020/11/2
 * @since 1.0.0
 */
public interface HttpEndpointRouter {

    String route(List<String> endpoints);
}
