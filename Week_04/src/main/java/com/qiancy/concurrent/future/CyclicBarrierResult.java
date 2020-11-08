package com.qiancy.concurrent.future;

import com.qiancy.concurrent.utils.GetFeiBo;

import java.util.concurrent.*;

/**
 * 功能简述：通过cylicBarrier获取返回值
 *
 * @author qiancy
 * @create 2020/11/8
 * @since 1.0.0
 */
public class CyclicBarrierResult {

    private static volatile Integer count = 0;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        ExecutorService executor = Executors.newCachedThreadPool();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, new Runnable() {
            @Override
            public void run() {
                //这是得到的返回值
                int result = count;
                // 确保  拿到result 并输出
                System.out.println("异步计算结果为：" + result);

                System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            }
        });
        // 异步执行 下面方法
        executor.execute(new SetNum(cyclicBarrier));

        // 然后退出main线程
        executor.shutdown();
    }
    private static int sum() {
        return GetFeiBo.fiBo(36);
    }


    static class SetNum implements Runnable{
        private CyclicBarrier cyc;
        public SetNum(CyclicBarrier cyc){
            this.cyc = cyc;
        }
        @Override
        public void run() {
            synchronized (this){
                count = sum();
                try {
                    cyc.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
