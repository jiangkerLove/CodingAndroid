# 线程的基本概念

* 线程之间是共享内存空间的
* 进程之间是有内存隔离的，进程之间的通信需要用到ipc通信
* 一般来说，线程只有在确保资源不会被改变或者它有独占访问权的时候才可以使用某个资源
* 多线程程序会在mian()方法以及所有非守护线程都返回的时候才退出
* ++线程的类别（是否为守护线程）和优先级属性是会被继承的，线程的这两个属性由开启该线程的线程决定。

# 缓存行的使用
* 当多个线程同时修改一个缓存行里面的多个变量时，由于同时只能有一个线程操作缓存行，所以相比将每个变量放到一个缓存行，性能会下降，这就是伪共享
* 地址连续的多个变量才有可能会被放到一个缓存行中，所以对于数组的访问就会快许多
* 单个线程下顺序修改一个缓存行中的多个变量，会充分利用程序运行的局部性原则，从而加速了程序的运行。而在多线程下并发修改一个缓存行中的多个变量时就会竞争缓存行，从而降低程序运行性能。

# 锁的分类：

* 悲观锁：对数据被外界修改持保守态度，认为数据很容易就会被其他线程修改，所以数据被处理前先对数据进行加锁，并在整个数据处理过程中，使数据处于锁定状态。
* 乐观锁：认为数据在一般情况下不会造成冲突，所以在访问记录前不会加排他锁，而在进行数据提交更新时，才会正式对数据冲突与否进行检测，可以避免程序死锁。
* 公平锁与非公平锁：公平锁表示线程获取锁的顺序是按照线程请求锁的时间早晚来决的，也就是最早请求锁的线程将最早获取到锁。而非公平锁则在运行时闯入，也就是先不一定先得。
    ```java
    //根据传入参数提供公平和非公平锁，不传入参数默认是非公平
    public ReentrantLock(boolean fair){
        sync = fair ? new FairSync() : new NonfairSync();
    }
    ```
    没有公平性需求的前提下尽量使用非公平锁，因为公平锁会带来额外的性能开销。
* 独占锁：任何时候都只有一个线程可以得到锁，ReentrantLock就是以独占方式实现的独占锁是一种悲观锁，限制了并发。
* 共享锁：可以由同时由多个线程持有，例如ReadWriteLock读写锁，允许一个资源可以多个线程同时进行读写操作。是一种乐观锁，放宽了加锁的条件。
* 可重入锁：当一个线程获取了一个锁，那么该线程可以重复的进入加了该锁的代码。sychronized内部锁就是可重入的，锁内部维护了一个线程标识，原来标识该锁目前被哪个程占用，然后关联了一个计数器记录进入次数。
* 自旋锁：当一个线程在获取锁失败后，会被切换到内核状态而被挂起。当该线程获取锁时，有需要将其切换到内核状态而唤醒该线程。而用户态切换到内核状态的开销是比大的，在一定程度上会影响并发性能。自旋锁则是在获取锁失败时，并不会放弃CPU使用，而是多次尝试获取锁。自旋锁是使用CPU时间来换取线程阻塞与调度的开销，但也可能白白浪费CPU时间。

# 运行线程的方法

[代码示例ThreadTest](../../../language/src/test/java/jfp/study/language/ThreadTest.kt)

- 继承至Thread类，重写run方法

```java
public class Main{
    public static void main(String[] args){
        TestThread thread = new TestThread();
        thread.start();
    }

    static class TestThread extends Thread{
        @Override
        public void run(){
            super.run();
        }
    }
}
```

- 实现Runnable接口

```Java
public class Main{
    public static void main(String[] args){
        Thread thread = new Thread(new TestRunnable());
        thread.start();
    }
    static class TestRunable implements Runable{
        @Override
        public void run(){
        }
    }
}
```

- 实现Callable接口

```java
public class Main{
    public static void main(String[] args) throws ExcutionException, InterruptedException{
        FutureTask<String> futureTask = new FutureTask<>(new TestCall());
        Thread thread = new Thread(futureTask);
        thread.start();
        //阻塞等待执行结束获取值
        futrueTask.get();
    }
    static class TestCall implements Callable<String> {
        @Override
        public String call(){
            return "Over";
        }
    }
}
```

# 生命周期
![image](https://jk-node-photo.oss-cn-hangzhou.aliyuncs.com/Java/thread/threadlife.png)

# Thread类介绍


```kotlin
class Thread{
    private val target: Runnable? = null,
    private var daemon: Boolean = false
}:Runable{

    //当前线程让出CPU的使用权，然后处于就绪状态
    external fun yield()

    //暂时让出CPU的使用权一定时间，不参与CPU的调度，但是不会让出已获取到的资源，例如锁
    @Throws(InterruptedException::class)
    external fun sleep(millis: Long)

    /**
     * 线程A调用了线程B的join方法后被阻塞，直到B线程结束
     * 或者其他线程调用了A的interrupt方法中断了线程A时，A会抛出InterruptedException异常
     */
    @Throws(InterruptedException::class)
    external fun join(millis: Long = 0L)

    //是否已经启动
    external fun isAlive(): Boolean

    /**
     * 设置是否为守护线程，守护线程不能阻止程序的结束
     */
    fun setDaemon(on: Boolean){
     //需要在线程启动之前设置
     if(isAlive()) { throw IllegalThreadStateException() }
     daemon = on
    }
    
    // 中断线程，设置中断标志，会使被挂起的线程直接抛出异常退出
    fun interrupt(){  }

    // 检测中断标志，不会清除标志
    fun isInterrupted(): Boolean { return isInterrupted(false) }

    //设置1-10的线程优先级
    fun setPriority(newPriority: Int){}

    //启动线程，然后进入就绪状态，不一定运行
    @Synchronized
    fun start() {}

    override fun run() { target?.run() }

    companion object {
        //获取当前线程的对象
        external fun currentThread(): Thread

        //检测并清除线程中断状态
        fun interrupted(): Boolean { return isInterrupted(true) }

        //检测线程中断状态，@ClearInterrupted 是否清除状态
        private external fun isInterrupted(ClearInterrupted: Boolean): Boolean
    }
}

/**
 * 这三个方法都需要在synchronized中才能调用
 */
class Object {
    //在当前对象同步块中，随机唤醒一个调用wait挂起的线程
    external fun notify()

    //在当前对象同步块中，唤醒该对象所有挂起的线程，依次获取到锁之后开始执行
    external fun notifyAll()

    /**
     * 再获取到一个对象的监视器锁之后，调用此方法的线程会被阻塞挂起
     * 直到其他现场调用了该共享对象的notify或者notifyAll方法或调用了该线程的intercept方法
     * 该线程抛出InterruptedException异常返回，可能也会存在低概率的虚假唤醒
     */
    @Throws(InterruptedException::class)
    external fun wait(timeout: Long = 0)
}
```
# 中断

> 设置了中断的线程，切换到等待状态是会直接抛出异常而终止等待的。

- public native boolean isInterrupted();检查中断标志，不清除结果
- public static native boolean interrupted();检测中断标志，清除结果
- public void interrupt()；添加中断标志

