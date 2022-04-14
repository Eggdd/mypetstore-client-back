package org.csu.mypetstoreclient.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.Log;
import org.csu.mypetstoreclient.persistence.LogMapper;
import org.csu.mypetstoreclient.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;


@Aspect
@Component
public class ControllerAspect {

    @Autowired
    private LogMapper logMapper;

    Log log = new Log();

    @Pointcut("execution(public * org.csu.mypetstoreclient.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        // 接到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();

        if (session.getAttribute("account") == null){
            log.setLog_user_id("unknown");
        } else{
            AccountVO account = (AccountVO) session.getAttribute("account");
            log.setLog_user_id(account.getUsername());
        }
        System.out.println("执行之前");
        // 获取请求时间
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        log.setLog_date(currentTime);

        // 记录下请求内容
        log.setUrl(request.getRequestURL().toString());

//        System.out.println("URL : " + request.getRequestURL().toString());
//        System.out.println("HTTP_METHOD : " + request.getMethod());
//        System.out.println("IP : " + request.getRemoteAddr());
//        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "webLog()", returning = "ret")
    public void doAfter(CommonResponse ret) throws Throwable{
        log.setResponse_msg(ret.getMsg());
        log.setResponse_status(ret.getStatus());
        if( ret.getData() != null){
            log.setResponse_data_type(ret.getData().getClass().toString());
        } else{
            log.setResponse_data_type(null);
        }

        // 插入数据库
        logMapper.insert(log);
    }
}
