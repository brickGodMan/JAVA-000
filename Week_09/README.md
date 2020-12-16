### 第九周作业
##### 第十七节课作业
##### 1.改造自定义RPC程序

将服务端写死查找接口实现类变成泛型和反射
```java
/**
*  在老师程序基础上改造 添加getBean方法
*/
public interface RpcfxResolver {

    Object resolve(String serviceClass);

    Object getBean(Class klass);

}

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

        try {
            Class klass = Class.forName(serviceClass);
            // 作业1：改成泛型和反射            
            Object service = resolver.getBean(klass);
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setException(new RpcfxException("500","RPC远程方法调用出错",e.getMessage()));
            response.setStatus(false);
            return response;
        }
    }
    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }
}
```
尝试将客户端动态代理改成字节码增强方式，添加统一异常处理
```java
/**
 *  在老师原有代码的基础上 create 方法使用字节码增强方式替换java动态代理
 * @author qiancy
 */
public final class RpcfxCGLib {
    static {
        ParserConfig.getGlobalInstance().addAccept("io.qiancy");
    }

    /**
     * 使用cglib 方式实现代理
     * @param serviceClass
     * @param url
     * @param <T>
     * @return
     */
    public static <T> T create(final Class<T> serviceClass, final String url) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback(new RpcfxInterceptor(serviceClass, url));
        return (T) enhancer.create();

    }

    public static class RpcfxInterceptor implements MethodInterceptor {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;

        public RpcfxInterceptor(Class<?> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            Long start = System.currentTimeMillis();
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(objects);

            RpcfxResponse response = post(request, url);
            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException
            System.out.printf("结束时间：%s ms\n",System.currentTimeMillis() - start);
            return JSON.parse(response.getResult().toString());
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: " + reqJson);
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: " + respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }
}
/**
 * 功能简述：添加统一异常处理类
 * 修改返回体RpcfxResponse 的异常类型Exception为RpcfxException 所有catch块捕捉异常后用RpcfxException封装后设置到返回体中
 * @author qiancy
 * @create 2020/12/16
 * @since 1.0.0
 */
public class RpcfxException extends RuntimeException {

    /**
     * 异常错误码
     **/
    private String code;

    /**
     * 异常描述
     **/
    private String msg;
    /**
     * 扩展异常描述（包括msg）
     **/
    private String extMsg;

    /**
     * @param code 错误码
     * @param msg  描述信息
     */
    public RpcfxException(String code, String msg, String extMsg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
        this.extMsg = extMsg;
    }
}
```
尝试使用netty + httpClient端传输方式

这里我使用了httpClient端作为传输方式 netty 未实现。以下是Rpcfx中http实现的方法
```java
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
```
