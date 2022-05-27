package com.Haven.handler;

import com.Haven.VO.Respond;
import com.Haven.exception.GlobalException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.Haven.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.Haven.enums.StatusCodeEnum.VALID_ERROR;

/**
 * 全局异常处理 GlobalExceptionHandler
 *
 * @author HavenJust
 * @date 20:52 周二 10 五月 2022年
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理服务异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = GlobalException.class)
    public Respond<?> errorHandler(GlobalException e) {
        return Respond.fail(e.getStatus(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Respond<?> errorHandler(MethodArgumentNotValidException e) {
        return Respond.fail(VALID_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 处理系统异常
     *
     * @param e 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public Respond<?> errorHandler(Exception e) {
        e.printStackTrace();
        return Respond.fail(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getDescribe());
    }
}
