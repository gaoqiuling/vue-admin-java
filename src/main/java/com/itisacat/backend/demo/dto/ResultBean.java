package com.itisacat.backend.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@ToString
public class ResultBean<T> implements Serializable {
    /**
     * 响应头
     */
    @Getter
    @Setter
    private ResponseStatus heads = new ResponseStatus();
    /**
     * 业务数据.
     */
    @Getter
    @Setter
    private T body;


    public ResultBean() {
        super();
    }

    public ResultBean(T result) {
        super();
        this.heads.code = HttpStatus.OK.value();
        this.heads.message = "success";
        this.body = result;
    }


    public ResultBean(int code, String msg, T data) {
        super();
        this.heads.code = code;
        this.heads.message = msg;
        this.body = data;
    }

    public ResultBean(int code, String msg) {
        super();
        this.heads.code = code;
        this.heads.message = msg;
    }

    public static ResultBean success() {
        return new ResultBean(HttpStatus.OK.value(), "success");
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(data);
    }

    public static ResultBean error(int code, String message) {
        return new ResultBean<>(code, message);
    }


    @JsonIgnore
    public void setCode(int code) {
        this.heads.code = code;
    }

    @JsonIgnore
    public void setMessage(String message) {
        this.heads.message = message;
    }

    @JsonIgnore
    public Integer getCode() {
        return this.heads.code;
    }

    @JsonIgnore
    public String getMessage() {
        return this.heads.message;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return null != this.heads && null != this. heads.code && this.heads.code == HttpStatus.OK.value();
    }

    @Getter
    @Setter
    @ToString
    public static class ResponseStatus {
        private Integer code;

        private String message;

        private String requestId;

    }

    public T getBody() {
        return body;
    }
}
