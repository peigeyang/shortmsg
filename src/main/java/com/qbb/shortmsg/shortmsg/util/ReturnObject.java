package com.qbb.shortmsg.shortmsg.util;

public class ReturnObject<E> {

    private String statusCode;
    private String msg;
    private E data;

    public ReturnObject() {
        super();
    }

    public ReturnObject(String statusCode, String msg, E data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

    public final static String SUCCESS = "000000";
    public final static String SUCCESS_MSG = "成功";
    public final static String SHORTMSG = "010010";
    public final static String SHORTMSG_MSG = "验证码过期";
    public final static String REGISTER_ERROR = "010020";
    public final static String REGISTER_MSG = "已存在该用户";
    public final static String ERROR = "010000";
    public final static String ERROR_MSG = "短信发送失败";

    public ReturnObject<E> returnObject(E data){
        return new ReturnObject<E>(SUCCESS,SUCCESS_MSG,data);
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
