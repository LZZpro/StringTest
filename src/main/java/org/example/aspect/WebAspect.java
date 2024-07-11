package org.example.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.domain.R;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/11 11:37
 */
@Aspect
@Component
public class WebAspect {
    Logger logger =  LoggerFactory.getLogger(WebAspect.class);
    @Pointcut("execution(public * org.example.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //过滤掉存在死循环的对象
        Object[] objects = Arrays.stream(joinPoint.getArgs()).filter(object -> !(object instanceof HttpServletResponse)).toArray();

        // 打印请求 url、Http method、controller 的全路径以及执行方法、请求的IP、请求入参
        logger.info(String.format("\n" +
                        "========================================== Start ==========================================\n" +
                        "URL            : %s\n" +
                        "HTTP Method    : %s\n" +
                        "Class Method   : %s.%s\n" +
                        "IP             : %s\n" +
                        "USER           : %s\n" +
                        "Request Args   : %s",
                request.getRequestURL().toString(), request.getMethod(), joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), request.getRemoteAddr(), request.getHeader("Authorization"), new ObjectMapper().writeValueAsString(objects)));
    }

    @After("webLog()")
    public void doAfter() throws Throwable {
        // 每个请求之间空一行
        logger.info("\n");
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        String response = "";
        if (result instanceof R) {
            response = new ObjectMapper().writeValueAsString(result);
        }
        logger.info(String.format("\n" +
                        "Response Args  : %s\n" +
                        "Time-Consuming : %s ms\n" +
                        "=========================================== End ===========================================",
                response, System.currentTimeMillis() - startTime));
        return result;
    }
}
