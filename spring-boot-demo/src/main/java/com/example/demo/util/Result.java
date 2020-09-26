package com.example.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Created By ShenXi
 * @Created On 2019/3/28 9:44
 * @Description :
 * @ClassName : Result
 */
public class Result<T> extends ResponseEntity<Message> {

    public Result(HttpStatus status) {
        super(status);
    }

    public Result(String code, String msg, T data) {
        super(Message.custom(code, msg, data), Message.num2HttpStatus(code));
    }

    public Result(String code, String msg) {
        super(Message.custom(code, msg), Message.num2HttpStatus(code));
    }

    public static <T> Result<T> success(T data) {
        return new Result("200", "成功", data);
    }

    public static <T> Result<T> failed(T data) {
        return new Result("422", "失败", data);
    }

    public static <T> Result<T> failMsg(String msg) {
        return new Result("422", msg);
    }

    public static <T> Result<T> custom(String code, String msg, T data) {
        return new Result(code, msg, data);
    }
}
