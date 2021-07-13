package com.overops.examples;

import java.util.concurrent.ExecutorService;

import com.overops.examples.controller.EventGenerator;
import com.overops.examples.domain.User;
import com.overops.examples.utils.EventType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventsRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(AbstractEventsRunnable.class);

    ExecutorService executorService;

    AbstractEventsRunnable(EventGenerator eventGenerator, ExecutorService executorService) {
        this.executorService = executorService;
    }

    public boolean generateEvent(User user, EventType eventType, EventCallable callable) {
        callable.setTarget(user, eventType);

        boolean eventGenerated = false;
        try {
            eventGenerated = executorService.submit(callable).get();
        } catch(Exception e) {
            log.error("THIS IS A BUG IN THE GENERATOR: " + e.getMessage(), e);
        }

        if (!eventGenerated) {
            log.error("Error: Unable to generate event " + eventType);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        return eventGenerated;
    }
}
