package com.sh.cloud.common;

import lombok.Data;

/**
 * Created by jxh on 2019/2/14.
 */
@Data
public class Result <T> {

    private int code = 200;
    private String result = "success";
    private String message = "";
    public String msg = "";
    public String type = "";
    private long count;
    private boolean is;
    private String tip;

    private T data;

    /**
     * 只返回错误码
     *
     * @param resultCode resultCode
     */
    public Result(int resultCode) {
        this.code = resultCode;
    }

    /**
     * 只有返回数据的(验证成功)
     *
     * @param data data
     */
    public Result(T data) {
        this.data = data;
    }

    /**
     * 只有错误码和错误信息的
     *
     * @param resultCode resultCode
     * @param message    message
     */
    public Result(int resultCode, String message) {
        this.code = resultCode;
        this.message = message;
    }


    /**
     * 全部参数
     *
     * @param resultCode resultCode
     * @param message    message
     * @param data       data
     */
    public Result(int resultCode, String message, T data) {
        this.code = resultCode;
        this.message = message;
        this.data = data;
    }
    public Result(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }
    /**
     * 全部参数
     *
     * @param resultCode resultCode
     * @param message    message
     * @param count       count
     * @param data       data
     */
    public Result(int resultCode, String message, long count, T data) {
        this.code = resultCode;
        this.message = message;
        this.count = count;
        this.data = data;
    }
    public Result(int resultCode, String message, long count, T data,boolean is,String tip) {
        this.code = resultCode;
        this.msg = message;
        this.count = count;
        this.data = data;
        this.is = is;
        this.tip = tip;
    }
}