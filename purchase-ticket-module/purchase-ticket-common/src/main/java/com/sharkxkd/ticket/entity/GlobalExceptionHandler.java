package com.sharkxkd.ticket.entity;

import com.sharkxkd.ticket.exception.AbstractException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sharkxkd.ticket.enums.errorEnum.ErrorCodeEnum.SERVICE_ERROR;


/**
 * 全局异常处理器
 * @author zc
 * @date 2024/11/27 19:08
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(AbstractException.class)
    public JsonResult<Void> handleServiceException(HttpServletRequest request, AbstractException e){
        //如果存在错误信息，需要日志记录错误信息
        if(e.getCause() != null){
            log.error("请求的url为{}，请求的方法对应{}，错误原因为{}",request.getRequestURL(),request.getMethod(),e.getCode(),e);
            return JsonResult.error(e);
        }
        log.error("请求的url为{}，请求的方法对应{}",request.getRequestURL(),request.getMethod(),e);
        return JsonResult.error(e);
    }

    /**
     * 拦截未被捕获的异常
     * @param request
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public JsonResult<Void> defaultErrorHandler(HttpServletRequest request, Throwable throwable) {
        log.error("[{}] {} ", request.getMethod(), getUrl(request), throwable);
        return JsonResult.error(SERVICE_ERROR);
    }

    private String getUrl(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getQueryString())) {
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }
}
