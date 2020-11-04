package io.qiancy.gateway.router;

import java.util.List;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/3
 * @since 1.0.0
 */
public class HttpEndpointRouterImpl implements HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {
        int index = (int) (Math.random() * endpoints.size());
        return endpoints.get(index);
    }
}
