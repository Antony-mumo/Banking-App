package com.company.system.controller;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ResponseWrapper<T> {
    private Integer code;
    private String status;
    private String message;
    private T data;

    public void setSuccess(T body){
        this.code = HttpStatus.OK.value();
        this.status = HttpStatus.OK.name();
        this.message = "success";
        this.data = body;
    }

    public void setStatus(HttpStatus httpStatus, T body){
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = httpStatus.getReasonPhrase();
        this.data = body;
    }

    public void failed(HttpStatus httpStatus, String message){
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

}
