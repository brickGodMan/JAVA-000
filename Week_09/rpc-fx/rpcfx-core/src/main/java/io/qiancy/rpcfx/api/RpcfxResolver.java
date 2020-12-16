package io.qiancy.rpcfx.api;

public interface RpcfxResolver {

    Object resolve(String serviceClass);

    Object getBean(Class klass);

}
