> Executor框架用于把任务的提交和执行解耦，任务的提交交给Runnable或Callable，而Executor用来处理任务。ThreadPoolExecutor时线程池的核心实现类。

[代码示例](../../../language/src/test/java/jfp/study/language/ThreadPoolTest.kt)

## ThreadPoolExecutor

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
```
![image](https://jk-node-photo.oss-cn-hangzhou.aliyuncs.com/Java/thread/threadPoolExecutor_main.png)

## FixedThreadPool

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```
创建只有固定核心线程数的线程池，超过线程数的任务都将放入到无界阻塞队列中。
![image](https://jk-node-photo.oss-cn-hangzhou.aliyuncs.com/Java/thread/fixedThreadPool.png)

## CacheThreadPool

```java
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```
创建无核心线程的线程池，提交的任务都会交给空闲线程或者创建新的线程去处理，空闲超过60s的线程将会被终止。适合大量的需要立即处理并且耗时较少的任务。

![image](https://jk-node-photo.oss-cn-hangzhou.aliyuncs.com/Java/thread/CachedThreadPool.png)

## SingleThreadExecutor

```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
```
只有一个核心线程，任务放到无界阻塞队列中，可以保证任务放到一个线程中顺序执行。
![image](https://jk-node-photo.oss-cn-hangzhou.aliyuncs.com/Java/thread/SingleThreadExecutor.png)

## ScheduledThreadPool

主要用于给定延时之后运行任务或者定期处理任务。
```java
public static ScheduledExecutorService newScheduledThreadPool(
        int corePoolSize, ThreadFactory threadFactory) {
    return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
}

public ScheduledThreadPoolExecutor(int corePoolSize,
                                   ThreadFactory threadFactory) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
          new DelayedWorkQueue(), threadFactory);
}
```
![image](https://jk-node-photo.oss-cn-hangzhou.aliyuncs.com/Java/thread/ScheduledThreadPool.png)

## newWorkStealingPool

支持给定并行度的线程，提供多个等待队列来减少竞争。默认线程数等于CPU数。

```java
public static ExecutorService newWorkStealingPool(int parallelism) {
    return new ForkJoinPool
        (parallelism,
         ForkJoinPool.defaultForkJoinWorkerThreadFactory,
         null, true);
}
```

## **execute和submit的区别**

- execute只能提交Runnable类型的任务，无返回值。submit既可以提交Runnable类型的任务，也可以提交Callable类型的任务，会有一个类型为Future的返回值，但当任务类型为Runnable时，返回值为null。
- execute在执行任务时，如果遇到异常会直接抛出，而submit不会直接抛出，只有在使用Future的get方法获取返回值时，才会抛出异常。

