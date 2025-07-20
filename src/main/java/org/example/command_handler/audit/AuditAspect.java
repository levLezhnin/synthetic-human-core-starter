package org.example.command_handler.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.messaging.LogEvent;
import org.example.messaging.LogProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    @Autowired
    private LogProducer logProducer;

    @Pointcut("@annotation(org.example.command_handler.audit.WeylandWatchingYou)")
    public void audit() {}

    @Around("audit()")
    public Object auditMethod(ProceedingJoinPoint jp) {

        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        String methodName = methodSignature.getMethod().getName();
        Object[] args = jp.getArgs();
        boolean logIntoConsole = methodSignature.getMethod().getAnnotation(WeylandWatchingYou.class).logIntoConsole();

        if (logIntoConsole) {
            log.info("Invoking method: {}, with params: {}", methodName, args);
            Object result = null;
            try {
                result = jp.proceed();
                log.info("Result: {}", result);
                return result;
            } catch (Throwable e) {
                log.info("Ended with exception: {}", e.getMessage());
            }
            return result;
        } else {
            Object result = null;
            try {
                result = jp.proceed();
                logProducer.sendLog(LogEvent.toLogEvent(methodName, args, result, ""));
            } catch (Throwable e) {
                logProducer.sendLog(LogEvent.toLogEvent(methodName, args, result, e.getMessage()));
            }
            return result;
        }
    }
}
