package com.model;

public class Demo {

   public static void main(String[] args) throws InterruptedException {
      //第一种状态切换- 新建->运行->终止
       System.out.println("############## 第一种状态切换- 新建->运行->终止 ###########################3");
       Thread thread1=new Thread(new Runnable() {
           @Override
           public void run() {
               System.out.println("thread1当前状态："+Thread.currentThread().getState().toString());
               System.out.println("thread1执行了");
           }
       });
       System.out.println("没有调用start thread1当前状态："+thread1.getState().toString());
       thread1.start();
       Thread.sleep(2000L);
      System.out.println("等待两秒  再看看当前线程状态："+thread1.getState().toString());
//       thread1.start();  TODO Exception in thread "main" java.lang.IllegalThreadStateException 线程终止后再次调用会报异常
     System.out.println();
     System.out.println("############## 第二种状态切换- 新建->运行->等待->运行->终止（sleep方式） ###########################3");
     Thread thread2=new Thread(new Runnable() {
         @Override
         public void run() {
             //将线程2等待1.5秒后自动唤醒
             try {
                 Thread.sleep(1500L);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             System.out.println("thread2当前状态："+Thread.currentThread().getState().toString());
             System.out.println("thread2执行了");
         }
     });
       System.out.println("没有调用start thread2当前状态："+thread2.getState().toString());
       thread2.start();
       System.out.println("调用start thread2当前状态："+thread2.getState().toString());
       Thread.sleep(200L);//等待200毫秒在看状态
       System.out.println("等待200毫秒 thread2当前状态："+thread2.getState().toString());
       Thread.sleep(3000L);//等待三秒在看状态
       System.out.println("等待三秒 thread2当前状态："+thread2.getState().toString());

   }
}
