# JAVA多线程

[TOC]

# 基本概念
### 进程：
一个计算机程序的运行实例，包含了需要执行的指令；有自己的独立地址空间，包含程序内容和数据；不同进程的地址空间是互相隔离的；进程拥有各种资源和状态信息，包括打开的文件、子进程和信号处理。

### 线程：
表示程序的执行流程，是CPU调度执行的基本单位；线程有自己的程序计数器、寄存器、堆栈和帧。同一进程中的线程共用相同的地址空间，同时共享进进程锁拥有的内存和其他资源。

线程指的是一个程序中的不同的执行路径：
```
package com.test.ppt;

public class test {

    public static void main(String[] args) {
        method1();
    }

    public static void method1() {
        method2();
    }

    public static void method2(){}
}
```

### 线程的生命周期
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190401185915236.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
在线程的生命周期中，它要经过5种状态
- 新建(New)
  使用 new 关键字和 Thread 类或其子类建立一个线程对象后，该线程对象就处于新建状态。它保持这个状态直到程序 start() 这个线程
- 就绪（Runnable）
  当线程对象调用了start()方法之后，该线程就进入就绪状态。就绪状态的线程处于就绪队列中，要等待JVM里线程调度器的调度。
- 运行（Running）
  如果就绪状态的线程获取 CPU 资源，就可以执行 run()，此时线程便处于运行状态。处于运行状态的线程最为复杂，它可以变为阻塞状态、就绪状态和死亡状态。
- 阻塞(Blocked)
  如果一个线程失去所占用资源之后，该线程就从运行状态进入阻塞状态。阻塞状态可以分为三种：
  - 等待阻塞：
    运行状态中的线程执行 wait() 方法，使线程进入到等待阻塞状态。
  - 同步阻塞：
    线程在获取 synchronized 同步锁失败(因为同步锁被其他线程占用)。
  - 其他阻塞：
    通过调用线程的 sleep() 或 join() 发出了 I/O 请求时，线程就会进入到阻塞状态。当sleep() 状态超时，join() 等待线程终止或超时，或者 I/O 处理完毕，线程重新转入就绪状态。
  - 注意:
    被阻塞的线程会在合适的时候重新进入就绪状态，注意是就绪状态而不是运行状态。也就是说，被阻塞线程的阻塞解除后，必须重新等待线程调度器再次调度它。
- 死亡(Dead)
  一个运行状态的线程完成任务或者其他终止条件发生时，该线程就切换到终止状态。
  线程变为死亡状态有如下3种方式：
  - run()或call()方法执行完成，线程正常结束。
  - 线程抛出一个未捕获的Exception或Error。
  - 直接调用该线程stop()方法来结束该线程——该方法容易导致死锁，通常不推荐使用。

### 创建线程
Java 提供了四种创建线程的方法：
- 继承Thread类，重写run方法；
- 实现Runnable接口，重写run方法；
- 通过 Callable 和 Future 创建线程;
- 通过线程池创建线程

##### 继承Thread类
- run()为线程类的核心方法，相当于主线程的main方法，是每个线程的入口。run()方法是由jvm创建完本地操作系统级线程后回调的方法，不可以手动调用（否则就是普通方法）
- 一个线程调用 两次start()方法将会抛出线程状态异常，也就是的start()只可以被调用一次
```
package com.test.multithreading;

public class MyThread extends Thread {

    private String name;

    MyThread(String name) {
        this.name = name;
    }

    public void run(){
        for (int i=0;i<5;i++){
            System.out.println(name+"："+i);
        }
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread("线程1");
        MyThread myThread2=new MyThread("线程2");
        MyThread myThread3=new MyThread("线程3");
        myThread.start();
        myThread2.start();
        myThread3.start();
    }
}
```

##### 实现Runnable接口
实现Runnable接口实现多线程可以避免单继承局限
```
package com.test.multithreading;

public class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("Running"+Thread.currentThread().getName()+"start");

        for(int i=4;i>0;i--) {
            // 线程睡眠
            System.out.println("Thread："+Thread.currentThread().getName()+"，"+i);
        }
        System.out.println("Thread "+Thread.currentThread().getName()+"exiting.");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        MyThread myThread2 = new MyThread();
        MyThread myThread3 = new MyThread();
        Thread thread = new Thread(myThread,"线程1");
        Thread thread2 = new Thread(myThread2,"线程2");
        Thread thread3 = new Thread(myThread3,"线程3");
        thread.start();
        thread2.start();
        thread3.start();
    }
}
```

### 通过 Callable 和 Future 创建线程
```
package com.test.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i=0;
        for(;i<5;i++){
            System.out.println(Thread.currentThread().getName()+"："+i);
        }

        // cal()方法有返回值
        return i;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> callable = new MyThread();
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask, "线程1");

        FutureTask<Integer> futureTask2 = new FutureTask<>(callable);
        Thread thread2 = new Thread(futureTask, "线程2");

        thread.start();
        thread2.start();
    }
}
```

##### Runnable和Callable的区别
- Runnable执行方法是run(),Callable是call()
- 实现Runnable接口的任务线程无返回值；实现Callable接口的任务线程能返回执行结果
- call方法可以抛出异常，run方法若有异常只能在内部消化

##### 注意
Callable接口支持返回执行结果，需要调用FutureTask.get()方法实现，此方法会阻塞主线程直到获取结果；当不调用此方法时，主线程不会阻塞！ 
如果线程出现异常，Future.get()会抛出throws InterruptedException或者ExecutionException；如果线程已经取消，会抛出CancellationException

### 通过线程池创建线程
```
public ThreadPollExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingDeque<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
```
这是 ThreadPoolExecutor 的构造方法，其中的参数含义如下：
- corePoolSize：核心线程池大小，当新的任务到线程池后，线程池会创建新的线程（即便有空闲线程），直到核心线程池已满
- maximumPoolSize：最大线程池大小，线程池能创建的线程的最大数目
- keepAliveTime：程池的工作线程空闲后，保存存活的时间
- TimeUnit：时间单位
- BlockingQueue< Runnable>：用来储存等待执行任务的队列
- threadFactory：线程工厂
- RejectedExeutionHandler：当队列和线程池都满时拒绝任务的策略
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190401201611970.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
##### 实例
```
package com.test.multithreading;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString()+"线程被抛弃！");
    }
}
```
```
package com.test.multithreading;

import java.util.concurrent.*;

public class ThreadPollExecutor {


    public static void main(String[] args) {

        RejectedExecutionHandler rejectedHandler = new RejectedExecutionHandlerImpl();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,3,200,
                TimeUnit.MICROSECONDS,new ArrayBlockingQueue<Runnable>(6),rejectedHandler);

        for(int i=0;i<9;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()
                    +"，队列中等待执行的任务数目："+executor.getQueue().size());
        }
        executor.shutdown();
    }


    static class MyTask implements Runnable{
        private int taskNum;

        public MyTask(int num){
            this.taskNum = num;
        }

        @Override
        public void run(){
            System.out.println("正在执行task "+taskNum);
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("task "+taskNum+"执行完毕！");
        }
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190401203807239.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
从执行结果可以看出，当线程池中线程的数目大于2时，便将任务放入任务缓存队列里面，当任务缓存队列满了之后，便创建新的线程。当队列和线程池都满了时拒绝任务的策略

线程池的创建还可以通过调用 Executors 的工厂方法来创建线程池。
但有缺陷：（来自《阿里巴巴 Java 开发手册》）
1. 【强制】获取单例对象需要保证线程安全，其中的方法也要保证线程安全。
  说明：资源驱动类、工具类、单例工厂类都需要注意。
2. 【强制】创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。
  正例：
  public class TimerTaskThread extends Thread { 
  public TimerTaskThread() { 
  super.setName("TimerTaskThread");
  ...
  } 
3. 【强制】线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
  说明：使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，解决资
  源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者
  “过度切换”的问题。
4. 【强制】线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样
  的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
  说明：Executors 返回的线程池对象的弊端如下：
  1）FixedThreadPool 和 SingleThreadPool:
  允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
  2）CachedThreadPool 和 ScheduledThreadPool:
  允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。
5. 【强制】SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为
  static，必须加锁，或者使用 DateUtils 工具类。
  正例：注意线程安全，使用 DateUtils。亦推荐如下处理：
  private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() { 
  @Override 
  protected DateFormat initialValue() { 
  return new SimpleDateFormat("yyyy-MM-dd"); 
  } 
  }; 
  说明：如果是 JDK8 的应用，可以使用 Instant 代替 Date，LocalDateTime 代替 Calendar，
  DateTimeFormatter 代替 SimpleDateFormat，官方给出的解释：simple beautiful strong
  immutable thread-safe。
6. 【强制】高并发时，同步调用应该去考量锁的性能损耗。能用无锁数据结构，就不要用锁；能
  锁区块，就不要锁整个方法体；能用对象锁，就不要用类锁。
  说明：尽可能使加锁的代码块工作量尽可能的小，避免在锁代码块中调用 RPC 方法。
7. 【强制】对多个资源、数据库表、对象同时加锁时，需要保持一致的加锁顺序，否则可能会造
  成死锁。
  说明：线程一需要对表 A、B、C 依次全部加锁后才可以进行更新操作，那么线程二的加锁顺序
  也必须是 A、B、C，否则可能出现死锁。
8. 【强制】并发修改同一记录时，避免更新丢失，需要加锁。要么在应用层加锁，要么在缓存加
  锁，要么在数据库层使用乐观锁，使用 version 作为更新依据。
  说明：如果每次访问冲突概率小于 20%，推荐使用乐观锁，否则使用悲观锁。乐观锁的重试次
  数不得小于 3 次。
9. 【强制】多线程并行处理定时任务时，Timer 运行多个 TimeTask 时，只要其中之一没有捕获
  抛出的异常，其它任务便会自动终止运行，使用 ScheduledExecutorService 则没有这个问题。

### 线程的常用方法
##### start()
使该线程开始执行，线程进入就绪状态
##### run()
线程进入执行状态
##### setName(String name)
设置线程名称
##### isAlive()
线程处于“新建”状态时，线程调用isAlive()方法返回false。在线程的run()方法结束之前，即没有进入死亡状态之前，线程调用isAlive()方法返回true.
##### setPriority(int priority)
更改线程的优先级。

每一个 Java 线程都有一个优先级，这样有助于操作系统确定线程的调度顺序。
Java 线程的优先级是一个整数，其取值范围是 1 （Thread.MIN_PRIORITY ） - 10（Thread.MAX_PRIORITY ）。
默认情况下，每一个线程都会分配一个优先级 NORM_PRIORITY（5）。

##### join()
可以通过join()方法使得一个线程强制运行，线程强制运行期间，其他线程无法运行，必须等待此线程完成之后，才可以继续运行。

```
package com.test.multithreading;

public class Run implements Runnable {
    
    @Override
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName()+"运行 -->"+i);
        }
    }

    public static void main(String[] args) {
       Run run = new Run();                            	// 实例化 Runnable 子类对象
        Thread thread = new Thread(run, "线程");  		// 实例化 Thread 对象
        thread.start();                                 // 启动线程
        for(int i=0;i<5;i++){
            if(i>2){
                try{
                    thread.join();                      // 线程强制运行
                }catch(InterruptedException e){}
            }
            System.out.println("Main线程运行 -->"+i);
        }
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190401212012143.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
##### currentThread()
```
	@Override
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName()+"运行 -->"+i);
        }
    }
```

##### yield()
暂停当前正在执行的线程对象，并执行其他线程，线程通过  yield  方法可以使具有相同优先级的线程获得处理器

```
package com.test.multithreading;

public class YieldTest implements Runnable {

    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            try {
                //Thread.sleep(500);
            } catch (Exception e) {}
            System.out.println(Thread.currentThread().getName() + "运行，i = " + i);

            if (i ==2) {
                System.out.println(Thread.currentThread().getName() + "礼让");
                Thread.yield();
            }
        }
    }

    public static void main(String args[]) {
        YieldTest yieldTest = new YieldTest();
        Thread t1 = new Thread(yieldTest, "线程A");
        //Thread t2 = new Thread(yieldTest, "线程B");
        t1.start();
        //t2.start();

        for (int i=0; i<10; i++) {
            try {
                //Thread.sleep(500);
            } catch (Exception e) {}
            System.out.println("主线程运行，i = " + i);

            if (i ==5) {
                System.out.println("主线程运行礼让");
                Thread.yield();
            }
        }
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402152838981.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

##### sleep(long millisec)
在指定的毫秒数内让当前正在执行的线程休眠（暂停执行），此操作受到系统计时器和调度程序精度和准确性的影响。

##### 线程同步
```
package com.test.multithreading;

public class Test {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();     // 定义线程对象
        Thread thread = new Thread(myThread);   // 定义Thread对象
        Thread thread2 = new Thread(myThread);  // 定义Thread对象
        Thread thread3 = new Thread(myThread);  // 定义Thread对象
        thread.setName("线程1");
        thread2.setName("线程2");
        thread3.setName("线程3");
        thread.start();
        thread2.start();
        thread3.start();
    }
}

class MyThread implements Runnable {

    private int ticket = 5;
    @Override
    public void run(){
        for(int i=0;i<100;i++){
            if(ticket>0){
                System.out.println(Thread.currentThread().getName()+"买到票，剩余票数："+ --ticket);
            }
        }
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402154421540.png)
很有可能就会出现以下情况
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402154522932.png)
要解决这种问题就要使用同步，所谓同步就是指多个操作在同一时间段内只能有一个线程进行，其他线程要等这个线程完成之后才能继续执行。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402154550461.png)

实现同步的方式
###### 同步方法 
即有synchronized关键字修饰的方法。由于java的每个对象都有一个内置锁，当用此关键字修饰方法时，内置锁会保护整个方法。在线程调用该方法前，需要获得内置锁，否则就处于阻塞状态。
```
 class MyThread implements Runnable {
    private int ticket = 5;
    @Override
    public void run(){
        for(int i=0;i<2;i++){
            if(ticket>0){
                System.out.println(Thread.currentThread().getName()+"买到票，剩余票数："+ --ticket);
            }else{
                System.out.println(Thread.currentThread().getName()+"没买到票");
            }
        }
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402154819514.png)
###### 同步代码块
即有synchronized关键字修饰的语句块。被该关键字修饰的语句块会自动被加上内置锁，从而实现同步
```
class MyThread implements Runnable {
    private int ticket = 5;
    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 2; i++) {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "买到票，剩余票数：" + --ticket);
                } else {
                    System.out.println(Thread.currentThread().getName() + "没买到票");
                }
            }
        }
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402155111805.png)
##### 死锁
所谓死锁是指两个或两个以上的线程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们都将无法推进下去。
```
package com.test.multithreading;

public class DeadLockTest {
    public static void main(String[] args) {

        Thread thread = new Thread(new DeadLock(true),"线程1");
        Thread thread2 = new Thread(new DeadLock(true),"线程2");

        thread.start();
        thread2.start();
    }
}

class DeadLock implements Runnable{

    private static Object object = new Object();
    private static Object object2 = new Object();
    private boolean flag;

    public DeadLock(boolean flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"运行");
        if(flag){
            synchronized(object){
                System.out.println(Thread.currentThread().getName()+"已经锁住object");
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                synchronized(object2){
                    System.out.println("1秒后："+Thread.currentThread().getName()+"锁住object2");
                }
            }
        }else{
            synchronized(object2){
                System.out.println(Thread.currentThread().getName()+"已经锁住object2");
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                synchronized(object){
                    System.out.println("1秒后："+Thread.currentThread().getName()+"锁住object");
                }
            }
        }
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402160332428.png)
###### 如何避免死锁
- 加锁顺序（线程按照一定的顺序加锁）
- 加锁时限（线程尝试获取锁的时候加上一定的时限，超过时限则放弃对该锁的请求，并释放自己占有的锁）
- 死锁检测（当一个线程获取锁的时候，会在相应的数据结构中记录下来，相同下，如果有线程请求锁，也会在相应的结构中记录下来。当一个线程请求失败时，需要遍历一下这个数据结构检查是否有死锁产生。）


##### 生产者消费者
生产者消费者模型具体来讲，就是在一个系统中，存在生产者和消费者两种角色，他们通过内存缓冲区进行通信，生产者生产消费者需要的资料，消费者把资料做成产品。生产消费者模式如下图。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402160516748.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)
生产者是一堆线程，消费者是另一堆线程，内存缓冲区可以使用List数组队列，数据类型只需要定义一个简单的类就好。关键是如何处理多线程之间的协作。这其实也是多线程通信的一个范例。
　在这个模型中，最关键就是内存缓冲区为空的时候消费者必须等待，而内存缓冲区满的时候，生产者必须等待。其他时候可以是个动态平衡。值得注意的是多线程对临界区资源的操作时候必须保证在读写中只能存在一个线程，所以需要设计锁的策略。
```
package com.test.multithreading;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerAndConsumer {

    private final int MAX_LEN = 10;
    private Queue<Integer> queue = new LinkedList<Integer>();

    class Producer extends Thread{
        @Override
        public void run(){
            producer();
        }

        private void producer() {
            while(true){
                synchronized(queue){
                    while(queue.size()==MAX_LEN){
                        queue.notify();
                        System.out.println("当前队列满");
                        try{
                            queue.wait();
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    queue.add(1);
                    queue.notify();
                    System.out.println("生产者生产一条任务，当前队列长度为："+queue.size());
                    try{
                        Thread.sleep(500);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    class Consumer extends Thread{
        @Override
        public void run(){
            consumer();
        }

        private void consumer() {
            while(true){
                synchronized (queue){
                    while(queue.size()==0){
                        queue.notify();
                        System.out.println("当前队列为空");
                        try{
                            queue.wait();
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    queue.notify();
                    System.out.println("消费者消费一条任务，当前队列长度为："+queue.size());
                    try{
                        Thread.sleep(500);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer producerAndConsumer = new ProducerAndConsumer();
        Producer producer = producerAndConsumer.new Producer();
        Consumer consumer = producerAndConsumer.new Consumer();
        producer.start();
        consumer.start();
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190402162356656.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1p5aE1lbW9yeQ==,size_16,color_FFFFFF,t_70)

###### 同步锁池：
同步锁必须选择多个线程共同的资源对象，而一个线程获得锁的时候，别的线程都在同步锁池等待获取锁；当那个线程释放同步锁了，其他线程便开始由CPU调度分配锁

###### 关于让线程等待和唤醒线程的方法
- wait():执行该方法的线程对象，释放同步锁，JVM会把该线程放到等待池中，等待其他线程唤醒该线程
- notify():执行该方法的线程唤醒在等待池中等待的任意一个线程，把线程转到锁池中等待（注意锁池和等待池的区别）

##### sleep和wait的区别
1. 这两个方法来自不同的类分别是，sleep来自Thread类，和wait来自Object类。
2. 最主要是sleep方法没有释放锁，而wait方法释放了锁，使得其他线程可以使用同步控制块或者方法。
3. 使用范围：wait，notify和notifyAll只能在同步控制方法或者同步控制块里面使用，而sleep可以在任何地方使用
4. sleep必须捕获异常，而wait，notify和notifyAll不需要捕获异常