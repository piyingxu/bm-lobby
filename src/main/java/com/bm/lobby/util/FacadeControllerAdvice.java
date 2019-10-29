package com.bm.lobby.util;

import com.bm.lobby.dto.base.RespResult;
import com.bm.lobby.dto.base.RespSystemCode;
import com.bm.lobby.dto.base.RespUtil;
import com.bm.lobby.dto.base.ServiceException;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.stream.Collectors;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/3/27 17:02
*/
@ControllerAdvice
public class FacadeControllerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(FacadeControllerAdvice.class);
    @Autowired
    private MessageSource messageSource;

    @Value("${spring.application.name:}")
    private String applicaitonName;

    /**
     * @description 处理熔断异常，其他系统异常
     * @author : hui.zhou
     * @date 2018/4/16 14:25
     */
    @ExceptionHandler(value = {HystrixBadRequestException.class, HystrixRuntimeException.class, HystrixTimeoutException.class, RetryableException.class, Exception.class})
    public ResponseEntity<RespResult> handleManagementSystemException(Exception ex) {
        logger.warn("===System Exception==>" + ex.getMessage(), ex);
        RespResult response = RespUtil.fail(RespSystemCode.SYSTEM_ERROR.getCode(), applicaitonName + " system error");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * @description 处理业务异常
     * @author : hui.zhou
     * @date 2018/4/16 14:26
     */
    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<RespResult> handleManagementSystemException(ServiceException ex) {
        RespResult response = RespUtil.fail(ex.getCode(), ex.getMsg());
        logger.warn("===ServiceException==>,返回结果：{}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @description 校验参数
     * @author : hui.zhou
     * @date 2018/4/16 14:27
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        RespResult respResult = buildApiResponseForBindingErrors(ex, bindingResult);
        return ResponseEntity.badRequest().body(respResult);
    }

    /**
     * 校验请求参数
     *
     * @param ex
     * @param bindingResult
     * @return
     */
    private RespResult buildApiResponseForBindingErrors(MethodArgumentNotValidException ex,
                                                        BindingResult bindingResult) {
        String code = RespSystemCode.PARAM_ERROR.getCode();
        String message = bindingResult.getAllErrors().stream()
                .map(oe -> messageSource.getMessage(oe, Locale.getDefault())).collect(Collectors.joining(", "));
        logger.warn(String.format("===MethodArgumentNotValidException==>[%s]", message));
        return RespUtil.fail(code, message);
    }
}
