package com.OneTech.common.myException;

public class MyException extends Exception {

    private static final long serialVersionUID = 1L;

    // 提供无参数的构造方法
    public MyException() {
    }

    // 提供一个有参数的构造方法，可自动生成
    public MyException(String message) {
        super(message);
    }

}
