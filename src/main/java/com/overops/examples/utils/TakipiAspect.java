package com.overops.examples.utils;

import com.takipi.sdk.v1.api.Takipi;
import com.takipi.sdk.v1.api.core.contexts.TakipiContext;
import com.takipi.sdk.v1.api.core.metrics.TakipiAverageMetric;
import com.takipi.sdk.v1.api.core.metrics.TakipiCountMetric;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class TakipiAspect {

    private final Takipi takipi;

    @Autowired
    public TakipiAspect(Takipi takipi) {
        this.takipi = takipi;
    }

    @Before("@annotation(com.overops.examples.utils.TakipiInvocationCounter)")
    public void count(JoinPoint joinPoint) throws Throwable {

        TakipiContext context = takipi.contexts().createContext(joinPoint.getSignature().getDeclaringType());

        TakipiCountMetric countMetric = takipi.metrics().createCountMetric("COUNT_" + joinPoint.getSignature().getName());

        countMetric.increment(context);

    }

    @Around("@annotation(com.overops.examples.utils.TakipiAverageExecutionTime)")
    public Object average(ProceedingJoinPoint joinPoint) throws Throwable {

        TakipiContext context = takipi.contexts().createContext(joinPoint.getSignature().getDeclaringType());

        String metricName = "AVERAGE_" + joinPoint.getSignature().getName();

        TakipiAverageMetric averageMetric = takipi.metrics().createAverageMetric(metricName);

        StopWatch sw = new StopWatch(metricName);

        sw.start();

        try {

            return joinPoint.proceed();

        } finally {

            sw.stop();

            averageMetric.update(context, sw.getTotalTimeMillis());
        }

    }
}
