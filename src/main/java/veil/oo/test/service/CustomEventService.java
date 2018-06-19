package veil.oo.test.service;

import com.takipi.sdk.v1.api.Takipi;
import com.takipi.sdk.v1.api.core.contexts.TakipiContext;
import com.takipi.sdk.v1.api.core.events.TakipiEvent;
import com.takipi.sdk.v1.api.core.metrics.TakipiCountMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import veil.oo.test.domain.User;

import javax.annotation.PreDestroy;

@Service
public class CustomEventService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Takipi takipi;

    private TakipiContext takipiContext;

    @Autowired
    public CustomEventService(Takipi takipi) {
        this.takipi = takipi;

        takipiContext = takipi.contexts().createContext(this.getClass());
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


            TakipiCountMetric countMetric = takipi.metrics().createCountMetric("Invocation Count Metric");

            countMetric.increment(takipiContext);


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
