package com.qiancy.concurrent.future;

import com.qiancy.concurrent.utils.GetFeiBo;

/**
 * 功能简述：通过接口获取异步线程结果
 *
 * @author qiancy
 * @create 2020/11/8
 * @since 1.0.0
 */
public class InterfaceResult {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        //这是得到的返回值
        setNum(result -> {
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        });
        // 然后退出main线程
    }

    private static int sum() {
        return GetFeiBo.fiBo(36);
    }


    private static void setNum(final IResult callBack) {
        Thread thread = new Thread(() -> {
            callBack.result(sum());
        });
        thread.start();
    }
}
