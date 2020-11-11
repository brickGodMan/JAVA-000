# 第四周作业
***
## 思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？
#### 1通过Future获取异步线程执行结果
&ensp;&ensp;&ensp;[代码地址](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/main/java/com/qiancy/concurrent/future/FutureOne.java)

&ensp;&ensp;&ensp;FutureOne：
```java
public class FutureOne {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        ExecutorService executor = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        //这是得到的返回值
        int result = future.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        executor.shutdown();
    }
    private static int sum() {
        return GetFeiBo.fiBo(36);
    }
}
```
#### 2通过FutureTask获取异步线程执行结果
&ensp;&ensp;&ensp;[代码地址](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/main/java/com/qiancy/concurrent/future/FutureTask.java)

&ensp;&ensp;&ensp;FutureTask：
```java
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
```
#### 3通过CountDown获取异步线程执行结果
&ensp;&ensp;&ensp;[代码地址](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/main/java/com/qiancy/concurrent/future/CountDown.java)
#### 4通过CyclicBarrierResult获取异步线程执行结果
&ensp;&ensp;&ensp;[代码地址](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/main/java/com/qiancy/concurrent/future/CyclicBarrierResult.java)
#### 5通过CompletableFuture获取异步线程执行结果
&ensp;&ensp;&ensp;[代码地址](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/main/java/com/qiancy/concurrent/future/CompletableFuture.java)
#### 6通过InterfaceResult获取异步线程执行结果
&ensp;&ensp;&ensp;[代码地址](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/main/java/com/qiancy/concurrent/future/InterfaceResult.java)
#### 7通过Join获取异步线程执行结果
&ensp;&ensp;&ensp;[代码地址](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/main/java/com/qiancy/concurrent/future/Join.java)
```java
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
```
#### 8通过Semaphore获取异步线程执行结果
&ensp;&ensp;&ensp;[代码地址](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/main/java/com/qiancy/concurrent/future/SemaphoreResult.java)
```java
public class SemaphoreResult {
    private static volatile Integer count = 0;

    public static void main(String[] args) throws  InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Semaphore semaphore = new Semaphore(1);
        final Sum sum = new Sum(semaphore,36);
        Thread thread = new Thread(sum);
        thread.start();
        thread.join();
        //这是得到的返回值
        int result = sum.getCount();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

    private static int sum() {
        return GetFeiBo.fiBo(36);
    }

    static class Sum implements Runnable{
        private Semaphore semaphore;
        private  Integer sum;
        private volatile Integer count;

        public Sum(Semaphore semaphore, Integer sum) {
            this.semaphore = semaphore;
            this.sum = sum;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                count = GetFeiBo.fiBo(sum);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private Integer getCount() {
            return this.count;
        }
    }
}
```
## 4.（必做）把多线程和并发相关知识带你梳理一遍，画一个脑图，截图上传到 Github 上
![](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/img/concurrentSummarize.png)