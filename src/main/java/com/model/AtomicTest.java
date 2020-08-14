package com.model;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程操作之原子性操作
 */
public class AtomicTest {
    int i=0;
    int value=0;
    AtomicInteger j=new AtomicInteger(0);

    static  Unsafe unsafe;
    private  static  long valueoffset;
    static {
        try {
            Field field=Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            //获取value 属性的偏移量（用于定位value属性在内存中的具体地址）
            valueoffset=unsafe.objectFieldOffset(AtomicTest.class.getDeclaredField("value"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add3(){
        j.incrementAndGet();//自增
    }
    public void add2(){
        //cas + 循环  重试
        int current;
        //unsafe.compareAndSwapInt(this,valueoffset,value,value+1);//这样写不能够保证每次都是成功的可能会失败
        do {
        current =unsafe.getIntVolatile(this,valueoffset);
        }while (!unsafe.compareAndSwapInt(this,valueoffset,current,current+1));//不失败则继续
    }


    Lock lock=new ReentrantLock();//锁
      public void add(){
          synchronized (this){//同步代码
              i++;
          }
      }
        public void add1(){
           lock.lock();
           try {
               i++;
           }finally {
             lock.unlock();
           }
        }

      public void test1(AtomicTest test) throws InterruptedException {
          for (int j = 0; j <2 ; j++) {
              new Thread(()->{
                  for (int k = 0; k <10000 ; k++) {
                      test.add();
                  }
              }).start();
          }
          Thread.sleep(5000L);
          System.out.println("输出："+i);
      }
    public void test2(AtomicTest test) throws InterruptedException {
        ExecutorService service=new ThreadPoolExecutor(0,2,5, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
        service.execute(new Runnable() {
            @Override
            public void run() {
                for (int k = 0; k <10000 ; k++) {
                    test.add();
                }
            }
        });
        Thread.sleep(2000L);
        System.out.println("输出："+i);
    }
    public static void main(String[] args) throws InterruptedException {
        AtomicTest d=new AtomicTest();
//        d.test1(d);
//        d.test2(d);
        for (int j = 0; j <2 ; j++) {
            new Thread(()->{
                for (int k = 0; k <10000 ; k++) {
//                    d.add();
//                    d.add1();
                    d.add2();
                    d.add3();
                }
            }).start();
        }
        Thread.sleep(2000L);
//        System.out.println("输出："+d.i);
//        System.out.println("输出："+d.value);
        System.out.println("输出："+d.j);

        //每次执行结果都会不同会产生竟态条件

        //避免这种情况的话要保障对共享资源的原子性
        //处理方式有 同步锁  保障线程安全 {同步代码块、  锁机制}

        //CAS机制  Compare and Swap  比较和交换  属于硬件同步原语  处理器提供了基本内存操作的原子性保证

        //CAS 操作输入的是两个参数一个旧值A，一个新值B 在操作期间 先比较旧值有没有变化如果没有发生变化才交换 新值，发生变化则不交换。


        //实现原理   java  提供了sun.misc.Unsafe类 compareAndSwapInt  compareAndSwapLong 等几个方法实现cas  该类是不安全的  要通过反射机制来获取实例

        //cas 底层的   复杂逻辑功能不建议使用

        //J.U.C  工具包就有cas 机制工具  java.util.conCurrent        eg add3
    }
}
