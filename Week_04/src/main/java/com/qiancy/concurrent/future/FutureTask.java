package com.qiancy.concurrent.future;

import com.qiancy.concurrent.utils.GetFeiBo;

import java.util.concurrent.*;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/8
 * @since 1.0.0
 */
public class FutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        ExecutorService executor = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        java.util.concurrent.FutureTask<Integer> futureTask = new java.util.concurrent.FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        executor.execute(futureTask);
        //这是得到的返回值
        int result = futureTask.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
        executor.shutdown();
    }

    private static int sum() {
        return GetFeiBo.fiBo(36);
    }


}
