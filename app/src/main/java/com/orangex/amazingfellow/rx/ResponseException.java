package com.orangex.amazingfellow.rx;

import com.alibaba.fastjson.JSONException;

import retrofit2.HttpException;

/**
 * Created by chengyuan.wang on 2017/10/30.
 * 链式调用的所有 Exception 都会转换成该类 ，直接为 UI 服务
 */

public class ResponseException extends Exception {
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 0x1;
    /**
     * http_错误
     */
    public static final int HTTP_ERROR = 0x2;

    /**
     * API 错误
     */
    public static final int API_ERROR = 0x3;
    /**
     * fastjson错误
     */
    public static final int JSON_ERROR = 0x4;
    /**
     * 未知错误
     */
    public static final int UNKNOWN_ERROR = 0x5;
    /**
     * 运行时异常-包含自定义异常
     */
    public static final int RUNTIME_ERROR = 0x6;
    /**
     * 无法解析该域名
     */
    public static final int UNKOWNHOST_ERROR = 0x7;


    private int code;
    //用于展示的异常信息
    private String displayMessage;


    public ResponseException(String message, Throwable throwable) {
        super(message, throwable);
    }



    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getCode() {
        return code;
    }

    public static ResponseException generateResponseException(Exception e) {

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //Log.w(TAG, httpException.getMessage(), httpException);
            ResponseException responseException = new ResponseException(httpException.getMessage(), httpException);
            responseException.setDisplayMessage(HTTP_ERROR + " 服务器异常");
            return responseException;
        } else if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            ResponseException responseException = new ResponseException(apiException.getMessage(), apiException);
            responseException.setDisplayMessage(API_ERROR + " 服务器异常");
            return responseException;
        } else if (e instanceof JSONException) {
            JSONException jsonException = (JSONException) e;
            ResponseException responseException = new ResponseException(jsonException.getMessage(), jsonException);
            responseException.setDisplayMessage(JSON_ERROR + " 结果解析异常");
            return responseException;
        } else {
            ResponseException responseException = new ResponseException(e.getMessage(), e);
            responseException.setDisplayMessage(UNKNOWN_ERROR + " " + e.getMessage());
            return responseException;
        }

    }
}
