package com.bm.lobby.util;

import com.alibaba.fastjson.JSONObject;
import com.bm.lobby.service.CommonService;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
* @author: yingxu.pi@transsnet.com
* @date: 2019/3/27 17:03
*/
@Component
@Aspect
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private CommonService commonService;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(Log)")
    public void advice() {
    }

    @Pointcut("@annotation(Log) || @annotation(io.swagger.annotations.ApiOperation)")
    public void exeAdvice() {
    }

    @Around("exeAdvice()")
    public Object aroundAdvice(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        Object[] args = point.getArgs();
        long time1 = System.currentTimeMillis();
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        ApiOperation api = method.getAnnotation(ApiOperation.class);
        Log log = method.getAnnotation(Log.class);
        List<Object> params = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof Serializable) {
                params.add(arg);
            }
        }
        String apidesc = "";
        String paramsStr = "";
        try {
            paramsStr = JSONObject.toJSONString(params);
        } catch (Exception e) {
            logger.warn("<=====请求参数json转化异常");
        }
        Object obj = null;
        String pid = null;
        if (!methodName.equals("login")) {
            pid = commonService.getCurrPid();
        }
        if (api != null) {
            apidesc = api.value();
            logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，请求参数req:{}, pid:{}", className, methodName, apidesc, paramsStr, pid);
            obj = point.proceed();
            long time2 = System.currentTimeMillis();
            logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，方法执行时长：{}ms，返回结果rsp:{}, pid:{}", className, methodName, apidesc, time2 - time1, JSONObject.toJSONString(obj), pid);
        } else if (log != null) {
            apidesc = log.value();
            if (log.before() && !log.duration()) {
                logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，请求参数req:{}, pid:{}", className, methodName, apidesc, paramsStr, pid);
            }
            obj = point.proceed();
            long time2 = System.currentTimeMillis();
            if (log.duration()) {
                logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，方法执行时长：{}ms, pid:{}", className, methodName, apidesc, time2 - time1, pid);
            } else {
                if (log.after()) {
                    logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，方法执行时长：{}ms，返回结果rsp:{}, pid:{}", className, methodName, apidesc, time2 - time1, JSONObject.toJSONString(obj), pid);
                }
            }
        } else {
            throw new RuntimeException("print exception");
        }
        return obj;

    }


}
