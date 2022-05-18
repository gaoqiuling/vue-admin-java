package com.itisacat.backend.demo.handler;

import com.itisacat.backend.demo.dto.ResultBean;
import com.itisacat.backend.demo.utils.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;

/**
 * @author lizhenjiang
 * @date 2020/4/27
 */
@RestControllerAdvice("com.itisacat.backend.demo.controller")
public class InternalExceptionHandler {

    /**
     * 拦截 post application/json  @valid校验不通过的请求
     *
     * @param e MethodArgumentNotValidException
     * @return 返回错误信息
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<?> validationException(MethodArgumentNotValidException e) {
        return dealBindingResult(e.getBindingResult());
    }

    private ResultBean<?> dealBindingResult(BindingResult bindingResult) {
        StringJoiner errInfo = new StringJoiner(",");
        bindingResult.getFieldErrors().forEach(s -> errInfo.add(s.getField() + ":" + s.getDefaultMessage()));
        return ResultBean.error(-40001, errInfo.toString());
    }


    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<?> parameterTypeException(BusinessException e) {
        return ResultBean.error(e.getCode(), e.getDesc());

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultBean<?> exceptionHandler(Exception e) throws BusinessException {
        return ResultBean.error(-50000, e.getMessage());
    }
}
