package com.overops.examples.service;

import com.overops.examples.domain.User;
import com.overops.examples.utils.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventService implements EventService {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void generateEvent(User user, EventType eventType) {
        log.info("generating [{}] for user {}", eventType, user.toString());

        fireEvent();
    }

    abstract void fireEvent();
}
