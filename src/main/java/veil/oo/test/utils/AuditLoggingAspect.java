package veil.oo.test.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class AuditLoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* veil.oo.test.service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.debug("before {}", joinPoint);
    }


    @After("execution(* veil.oo.test.service.*.*(..))")
    public void after(JoinPoint joinPoint) {
        log.debug("after {}", joinPoint);
    }

    @Around("execution(* veil.oo.test.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch sw = new StopWatch(joinPoint.toString());

        sw.start();

        try {

            return joinPoint.proceed();

        } finally {

            log.trace(sw.shortSummary());

            sw.stop();
        }


    }

}



