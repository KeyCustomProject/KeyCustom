package com.ho.edcustom.Exception;

import com.ho.edcustom.DTO.Response.HttpResponse;


public class CustomException extends RuntimeException{
    private HttpResponse httpResponse;

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//        //stacktrace 없애는 코드 필요시 이 코드 주석 처리
//    }
    public CustomException(String message, HttpResponse httpResponse) {
        super(message);
        this.httpResponse = httpResponse;
    }

    public CustomException(HttpResponse httpResponse) {
        super(httpResponse.getMessage());
        this.httpResponse = httpResponse;
    }
}
