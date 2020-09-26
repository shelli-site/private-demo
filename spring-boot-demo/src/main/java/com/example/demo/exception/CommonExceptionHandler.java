package com.example.demo.exception;

import com.example.demo.util.Message;
import com.example.demo.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.util.Message.num2HttpStatus;

/**
 * @Created By ShenXi
 * @Created On 2019/3/27 14:58
 * @Description :
 * @ClassName : CommonExceptionHandler
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {


    /**
     * @Param: [e]
     * @return: com.example.demo.util.Result
     * @Date: On 2019/3/28 10:21
     * @Author: ShenXi
     * @Description 拦截Exception类的异常
     **/
    // @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        // e.printStackTrace();
        Result result = new Result("500", e.getMessage());
        //正常开发中，可创建一个统一响应实体，如CommonResp
        return result;
    }

    /**
     * @Param: [e]
     * @return: com.example.demo.util.Result
     * @Date: On 2019/3/27 23:36
     * @Author: ShenXi
     * @Description 拦截TransactionSystemException类的异常
     **/
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseBody
    public ResponseEntity<Message> transactionSystemException(TransactionSystemException e) {

        Throwable t = e.getCause();
        while ((t != null) && !(t instanceof ConstraintViolationException)) {
            t = t.getCause();
        }
        List<String> msgList = new ArrayList<>();
        if (t instanceof ConstraintViolationException) {
            ConstraintViolationException con = (ConstraintViolationException) t;
            for (ConstraintViolation<?> constraintViolation : con.getConstraintViolations()) {
                msgList.add(constraintViolation.getMessage());
            }
        }
        log.warn(StringUtils.join(msgList.toArray(), ";"));
        Result result = new Result("422", StringUtils.join(msgList.toArray(), ";"), "");
        return result;
    }

}
