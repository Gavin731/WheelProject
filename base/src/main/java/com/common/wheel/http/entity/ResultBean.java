package com.common.wheel.http.entity;

/** 为泛型封装请求结果  ResultBean<ReturnBean<T>>
 * @author zhaosengshan
 */
public class ResultBean<T> {


    private int code;
    private Object message;
    private T resultData;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public T getResultData() {
        return resultData;
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
                ", resultData=" + resultData +
                ", data=" + data +
                '}';
    }
}
