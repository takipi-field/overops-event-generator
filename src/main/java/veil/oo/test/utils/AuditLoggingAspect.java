package veil.oo.test.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
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
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch sw = new StopWatch(joinPoint.toString());
        sw.start();

        joinPoint.proceed();

        sw.stop();

        log.trace(sw.shortSummary());
    }

}



