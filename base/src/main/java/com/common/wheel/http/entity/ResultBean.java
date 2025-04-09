package com.common.wheel.http.entity;

/** 为泛型封装请求结果  ResultBean<ReturnBean<T>>
 * @author zhaosengshan
 */
public class ResultBean<T> {


    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
