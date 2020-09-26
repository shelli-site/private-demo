package com.example.demo.util;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Created By ShenXi
 * @Created On 2019/3/25 21:06
 * @Description :
 * @ClassName : Message
 */
@Data
public class Message<T> {

    String status;
    //向前端返回的内容
    String message;

    T data;

    public Message() {
    }

    public Message(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Message(String status, String message, T data) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public static <T> Message<T> custom(String status, String message, T data) {
        return new Message(status, message, data);
    }

    public static <T> Message<T> custom(String status, String message) {
        return new Message(status, message);
    }

    public static HttpStatus num2HttpStatus(String code) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        for (HttpStatus httpStatus : HttpStatus.values()) {
            boolean b = Integer.parseInt(code) == httpStatus.value();
            if (b) {
                return httpStatus;
            }
        }
        return status;
    }

}