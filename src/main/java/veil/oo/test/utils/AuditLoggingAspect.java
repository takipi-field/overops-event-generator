package veil.oo.test.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AuditLoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* veil.oo.test.service.*.*(..))")
    public void before(JoinPoint joinPoint){
        log.debug(" before {}", joinPoint);
    }


    @After("execution(* veil.oo.test.service.*.*(..))")
    public void after(JoinPoint joinPoint){
        log.debug(" after {}", joinPoint);
    }

}



