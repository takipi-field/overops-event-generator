package veil.oo.test.service;

import com.takipi.sdk.v1.api.Takipi;
import com.takipi.sdk.v1.api.core.events.TakipiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import veil.oo.test.domain.User;

@Service
public class CustomEventService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final Takipi takipi = Takipi.create("CUSTOM_EVENT_SERVICE");


    public void fireCustomEvent(User demoUser, boolean generateEvent) {

        log.trace("user details: {}", demoUser.toString());

        if (generateEvent) {
            TakipiEvent customEvent = takipi.events().createEvent("Important Custom Event");

            log.info("firing custom event because this an important point in my code");

            customEvent.fire();
        }
    }
}
