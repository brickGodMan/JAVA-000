package io.qiancy.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.qiancy.rpcfx.api.RpcfxRequest;
import io.qiancy.rpcfx.api.RpcfxResponse;
import io.qiancy.rpcfx.exception.RpcfxException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.qiancy");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {

        // 0. 替换动态代理 -> AOP
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));

    }

    public static class RpcfxInvocationHandler implements InvocationHandler {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;

        public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
        // int byte char float double long bool
        // [], data class

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(params);

//            RpcfxResponse response = post(request, url);
            RpcfxResponse response = httpPost(request, url);

            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException

            return JSON.parse(response.getResult().toString());
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: " + reqJson);

            // 1.可以复用client
            // 2.尝试使用httpclient或者netty client
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: " + respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }

        private RpcfxResponse httpPost(RpcfxRequest req, String url) throws IOException {
            //设置请求配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(200)
                    .setConnectionRequestTimeout(200)
                    .setSocketTimeout(200)
                    .build();

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("Content-Type", "application/json; charset=utf-8");
            try {
                CloseableHttpResponse reponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = reponse.getEntity();
                String content = "";
                if (httpEntity != null) {
                    content = EntityUtils.toString(httpEntity, "UTF-8");
                }
                System.out.println(String.format("rsp content: %s", content));
                return JSON.parseObject(content, RpcfxResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
                RpcfxResponse rpcfxResponse = new RpcfxResponse();
                rpcfxResponse.setResult(null);
                rpcfxResponse.setStatus(false);
                rpcfxResponse.setException(new RpcfxException("500","调用远程方法失败",e.getMessage()));
                return rpcfxResponse;
            }
        }
    }
}
