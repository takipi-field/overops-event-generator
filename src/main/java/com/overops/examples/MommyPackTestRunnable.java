package com.overops.examples;

import java.util.concurrent.Executors;

import com.overops.examples.controller.EventGenerator;
import com.overops.examples.domain.User;
import com.overops.examples.utils.EventType;

class MommyPackTestRunnable extends AbstractEventsRunnable {

    User user;

    final EventCallable1 eventCallable1;
    final EventCallable2 eventCallable2;

    MommyPackTestRunnable(EventGenerator eventGenerator, User user) {
        super(eventGenerator, Executors.newFixedThreadPool(5));
        this.user = user;
        eventCallable1 = new EventCallable1(eventGenerator);
        eventCallable2 = new EventCallable2(eventGenerator);
    }

    @Override
    public void run() {
        // Throw the same exception from the same method multiple times. There will be 2 different 
        // entry points (eventCallable1 & eventCallable2). This should result in 
        // the creation of a mommy pack, with two children representing each entry point.
        // Hit counts for the two child requests should be 2 & 3.
        generateEvent(user, EventType.CAUGHT_EXCEPTION, eventCallable1);
        generateEvent(user, EventType.CAUGHT_EXCEPTION, eventCallable1);
        generateEvent(user, EventType.CAUGHT_EXCEPTION, eventCallable2);
        generateEvent(user, EventType.CAUGHT_EXCEPTION, eventCallable2);
        generateEvent(user, EventType.CAUGHT_EXCEPTION, eventCallable2);

        // Here's the same exception thrown from but thrown from a different method. This should result in 
        // the creation of another mommy pack, with two children representing each entry point (eventCallable1 & eventCallable2).
        // Hit counts for the two child requests should be 4 & 5.
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable1);
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable1);
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable1);
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable1);
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable2);
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable2);
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable2);
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable2);
        generateEvent(user, EventType.CATCH_IN_DIFFERENT_METHOD, eventCallable2);
    };
    
}
