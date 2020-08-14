package com.model.future;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Task
 * @Description TODO
 * @Author fyh
 * @Date 2020/5/9 11:10
 */
public class Task implements Callable<Result> {
    private String name;

    public Task(String name) {
        this.name = name;
    }
    @Override
    public Result call() throws Exception {
        int value = 0;

        for (int i = 0; i < 5; i++) {
            value += (int) (Math.random() * 100);
        }

        return new Result(this.name, value);
    }
}
