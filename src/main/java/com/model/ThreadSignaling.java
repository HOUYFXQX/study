package com.model;

import java.util.Objects;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程之间通信
 * 多种形式
 */
public class ThreadSignaling {
          public static Object baozidian=null;
          //正常的wait notify、notifyAll要在同步代码块中使用   使用线程公用一把锁
          public void testWaitNotify() throws InterruptedException {
              new Thread(()->{
                  if(Objects.isNull(baozidian)){//枚豹子则等待
                      synchronized (this)
                      {
                          System.out.println("1:等待状态！");
                          try {
                              this.wait();
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                      }
                  }
              System.out.println("2:买到包子回家！");
              }).start();
              //3秒之后生产一个包子
              Thread.sleep(3000L);
              baozidian=new Object();
              synchronized (this){
                  this.notifyAll();
                  System.out.println("3：通知消费者");
              }
          }
        //会导致永久等待的wait notify、notifyAll要在同步代码块中使用   使用线程公用一把锁所以wait 和notify使用要有先后顺序
        public void testWaitNotifyDeadLockTest() throws InterruptedException {
            new Thread(()->{
                if(Objects.isNull(baozidian)){//枚豹子则等待
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (this)
                    {
                        System.out.println("1:等待状态！");
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("2:买到包子回家！");
            }).start();
            //3秒之后生产一个包子
            Thread.sleep(3000l);
            baozidian=new Object();
            synchronized (this){
                this.notifyAll();
                System.out.println("3：通知消费者");
            }
        }
//   park 和unpark 的使用   不用考虑顺序    线程调用park 则等待许可   调用unpark 为指定线程提供许可（permit）
    //调用park线程会直接运行  同步代码块中容易死锁
    public void testParkOrUnpark() throws InterruptedException {
         Thread customerThread=new Thread(()->{
           if(Objects.isNull(baozidian)){//没包子
               System.out.println("1:等待状态！");
               LockSupport.park();
           }
           System.out.println("2:买到包子回家！");
         });
         customerThread.start();
        //3秒之后生产一个包子
        Thread.sleep(3000l);
        baozidian=new Object();
          LockSupport.unpark(customerThread);
        System.out.println("3：通知消费者");
    }

    /*死锁的park unpark*/
    public void parkDeadLocktest() throws InterruptedException {
        Thread customerThread=new Thread(()->{
            if(Objects.isNull(baozidian)){//没包子
                System.out.println("1:等待状态！");
                synchronized (this){//这里挂起之后不会释放锁所以一直死锁
                    LockSupport.park();
                }
            }
            System.out.println("2:买到包子回家！");
        });
        customerThread.start();
        //3秒之后生产一个包子
        Thread.sleep(3000l);
        baozidian=new Object();
        synchronized (this){
            LockSupport.unpark(customerThread);
        }
        System.out.println("3：通知消费者");
    }
    //


    public static void main(String[] args) throws InterruptedException {
        ThreadSignaling obj=new ThreadSignaling();
//        obj.testWaitNotify();
//        obj.testWaitNotifyDeadLockTest();
//        obj.testParkOrUnpark();
        obj.parkDeadLocktest();

        /**
         * 警告！之前代码中使用的if语句判断，是否进入等待状态是错误的！
         * 官方建议应该在while循环中检查等待条件，原因是处于等待状态的线程可能受会收到错误警报和伪唤醒，
         * 如果不在循环中检查等待条件，程序就会在没有满足结束条件的情况下结束。
         *
         *
         *
         *
         * 伪唤醒是指线程并非因为notify 、notifyall、unpark、等api调用而唤醒，是更底层的原因导致的。
         */

        /**
         * 伪代码有
         *
         * //wait
         *  synchronized (obj){
         *  while（<条件判断>）{
         *   obj.wait();
         *   ..//执行后续操作
         *  }
         *   }
         *
         *   //park
         *   while（<条件判断>）{
         *           LockSupport.park();
         *          ..//执行后续操作
         *          }
         *
         */

    }



}
