package cn.wf.simplespider.exception;

import cn.wf.simplespider.enums.ErrorStatus;
import cn.wf.simplespider.model.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;


@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    /**
     * 系统错误
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleException(Exception e) {
        return Resp.failure(ErrorStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 参数校验异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Resp handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String me = e.getBindingResult().getFieldError().getDefaultMessage();
        List<Map<String, Object>> fields = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            Map<String, Object> field = new HashMap<>(2);
            field.put("field", fieldError.getField());
            field.put("message", fieldError.getDefaultMessage());
            fields.add(field);
        }
        return Resp.failure(ErrorStatus.ILLEGAL_ARGUMENT);
    }

    /**
     * 空指针
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleNullPointerException(NullPointerException e) {
        return Resp.failure(ErrorStatus.SERVICE_EXCEPTION);
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleBindException(BindException e) {
        List<Map<String, Object>> fields = new ArrayList<>();
        for (FieldError error : e.getFieldErrors()) {
            Map<String, Object> field = new HashMap<>(2);
            field.put("field", error.getField());
            field.put("message", error.getDefaultMessage());
            fields.add(field);
        }
        return Resp.failure(ErrorStatus.ILLEGAL_ARGUMENT);
    }

    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleMultipartException() {
        return Resp.failure(ErrorStatus.MULTIPART_TOO_LARGE);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Resp handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Resp.failure(ErrorStatus.ILLEGAL_ARGUMENT);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleIllegalArgumentException(IllegalArgumentException e) {
        return Resp.failure(ErrorStatus.ILLEGAL_ARGUMENT);
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        return Resp.failure(ErrorStatus.MISSING_ARGUMENT);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleMethodArgumentTypeMismatchExceptionException(
            MethodArgumentTypeMismatchException e) {
        return Resp.failure(ErrorStatus.ILLEGAL_ARGUMENT);
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Resp handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Resp.failure(ErrorStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Resp handleNoHandlerFoundException(NoHandlerFoundException e) {
        return Resp.failure(ErrorStatus.METHOD_NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(TimeoutException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleTimeoutException(TimeoutException e) {
        return Resp.failure(ErrorStatus.SERVICE_EXCEPTION);
    }


    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleIllegalStateException(IllegalStateException e) {
        return Resp.failure(ErrorStatus.ILLEGAL_STATE);
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return Resp.failure(ErrorStatus.MEDIA_TYPE_NOT_ALLOWED);
    }

}