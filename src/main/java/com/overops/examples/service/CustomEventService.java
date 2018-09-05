package com.overops.examples.service;

import com.overops.examples.domain.User;
import com.takipi.sdk.v1.api.Takipi;
import com.takipi.sdk.v1.api.core.contexts.TakipiContext;
import com.takipi.sdk.v1.api.core.events.TakipiEvent;
import com.takipi.sdk.v1.api.core.metrics.TakipiCountMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
public class CustomEventService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Takipi takipi;

    private TakipiContext takipiContext = null;

    @Autowired
    public CustomEventService(Takipi takipi) {
        this.takipi = takipi;

        Class clazz = this.getClass();

        log.info("creating context for class : {}", clazz);

        try {

            takipiContext = takipi.contexts().createContext(clazz);

        } catch (Exception e) {

            log.error("there was a problem creating context for class " + clazz + ": " + e.getMessage(), e);

        }
    }


    public void fireCustomEvent(User demoUser, boolean generateEvent) {

        log.trace("user details: {}", demoUser.toString());

        if (generateEvent) {

            /*

                Custom Event Scenario:

                The OverOps SDK allows you to capture meaningful events in your code.

             */

            log.info("firing custom event because this an important point in my code");

            TakipiEvent customEvent = takipi.events().createEvent("Important Custom Event");

            customEvent.fire();

            if (takipiContext != null) {

                TakipiCountMetric countMetric = takipi.metrics().createCountMetric("Invocation Count Metric");

                countMetric.increment(takipiContext);

            } else {
                log.warn("TakipiContext is null; this is an SDK bug");
            }


        }
    }

    @PreDestroy
    public void destroyTakipiContext() {
        if (takipiContext != null) {

            log.trace("destroying TakipiContext: {}", takipiContext.toString());

            takipiContext.dispose();
        }
    }
}
