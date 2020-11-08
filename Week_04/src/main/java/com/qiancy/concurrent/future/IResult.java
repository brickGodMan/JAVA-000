package com.qiancy.concurrent.future;

/**
 * 功能简述：通过回调接口的方式获取返回值
 *
 * @author qiancy
 * @create 2020/11/8
 * @since 1.0.0
 */
public interface IResult {

    /**
     * 获取异步线程返回值
     * @param value
     */
     void result(Integer value);
}
