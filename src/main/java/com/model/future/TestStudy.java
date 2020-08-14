package com.model.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName TestStudy
 * @Description
 *  * 运行多个任务并处理全部结果
 *  *
 *  * 该范例的关键点在于调用了ExecutorService的invokeAll()方法。
 *  * 这个方法接收一个实现了Callable接口的任务列表，然后等待所有任务完成，并返回一个Future列表。
 * @Author fyh
 * @Date 2020/5/9 11:11
 */
public class TestStudy
{
    public static void main(String[] args){
        ExecutorService executor = Executors.newCachedThreadPool();

        List<Task> tasks = new ArrayList<Task>();

        for (int i = 0; i < 3; i++) {
            tasks.add(new Task(String.valueOf(i)));
        }
        try {
            List<Future<Result>> futures = executor.invokeAll(tasks);
            //List<Future<Result>> futures = executor.invokeAll(tasks, 2, TimeUnit.SECONDS);

            executor.shutdown();

            System.out.printf("Main: Start print resutls\n");

            for (Future<Result> future : futures) {
                System.out.printf("%s : %s\n", future.get().getName(), future.get().getValue());
                //System.out.println(future.isCancelled());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.printf("Main: End of programe.\n");
    }
}
