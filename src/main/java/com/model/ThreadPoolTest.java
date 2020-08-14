package com.model;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    /**
     * 1,线程池信息：核心线程数量5，最大线程数量，无界队列 ，超出核心线程数量的线程存活时间为5秒，指定拒绝策略的
     */
    private void threadPoolTest1() throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(5,10,5, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
        testCommon(threadPoolExecutor);
    }
   public void testCommon(ThreadPoolExecutor threadPoolExecutor) throws InterruptedException {
       for (int i = 0; i <15 ; i++) {
           int n=i;
           threadPoolExecutor.execute(new Runnable() {
               @Override
               public void run() {
                   try {
                       System.out.println("开始执行："+n);
                       Thread.sleep(3000L);
                       System.err.println("执行结束："+n);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           });
           System.out.println("任务提交成功:"+i);
       }
       //查看线程数量，查看队列等待数量
       Thread.sleep(500L);
       System.out.println("当前线程池线程数量为："+threadPoolExecutor.getPoolSize());
       System.out.println("当前线程池队列等待数量为："+threadPoolExecutor.getQueue().size());
       //等待15查看贤臣三个户口想和队列数量（理论上被超出核心线程数量的线程会被自动销毁）
       Thread.sleep(15000L);
       System.out.println("当前线程池线程数量为："+threadPoolExecutor.getPoolSize());
       System.out.println("当前线程池队列等待数量为："+threadPoolExecutor.getQueue().size());
   }
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolTest obj=new ThreadPoolTest();
        obj.threadPoolTest1();
    } 
}
