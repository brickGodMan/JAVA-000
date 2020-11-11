package com.qiancy.concurrent.future;

import com.qiancy.concurrent.utils.GetFeiBo;

import java.util.concurrent.*;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/11
 * @since 1.0.0
 */
public class Join {
    private static volatile Integer count = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Thread thread = new Thread(() -> {
            synchronized (count){
                count = sum();
            }
        });
        thread.start();
        thread.join();
        //这是得到的返回值
        int result = count;
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

    private static int sum() {
        return GetFeiBo.fiBo(36);
    }
}
