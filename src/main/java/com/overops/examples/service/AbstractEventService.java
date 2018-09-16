package com.overops.examples.service;

import com.overops.examples.domain.User;
import com.overops.examples.utils.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventService implements EventService {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void generateEvent(User user, boolean generateEvent, EventType eventType) {

        if (generateEvent) {
            log.info("generating [{}] for user {}", eventType, user.toString());
        }


        fireEvent(generateEvent);
    }

    abstract void fireEvent(boolean generateEvent);
}
