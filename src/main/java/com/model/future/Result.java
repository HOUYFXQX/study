package com.model.future;

/**
 * @ClassName Result
 * @Description 创建Result类，作为Callable接口实现call()方法的返回值类型
 * @Author fyh
 * @Date 2020/5/9 11:09
 */
public class Result {
    private String name;
    private int value;

    public Result(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
