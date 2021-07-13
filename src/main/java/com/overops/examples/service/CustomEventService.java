package com.overops.examples.service;

import com.takipi.sdk.v1.api.Takipi;
import com.takipi.sdk.v1.api.core.contexts.TakipiContext;
import com.takipi.sdk.v1.api.core.events.TakipiEvent;
import com.takipi.sdk.v1.api.core.metrics.TakipiCountMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
public class CustomEventService extends AbstractEventService {

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


    @PreDestroy
    public void destroyTakipiContext() {
        if (takipiContext != null) {

            log.info("destroying TakipiContext: {}", takipiContext.toString());

            takipiContext.dispose();
        }
    }

    @Override
    void fireEvent() {

        /*

            Custom Event Scenario:

            The OverOps SDK allows you to capture meaningful events in your code.

         */

        TakipiEvent customEvent = takipi.events().createEvent("Custom OverOps Event");
        
        customEvent.fire();

        if (takipiContext != null) {

            TakipiCountMetric countMetric = takipi.metrics().createCountMetric("Invocation Count Metric");

            countMetric.increment(takipiContext);

        } else {
            log.warn("TakipiContext is null; this is an SDK bug");
        }

    }
}
