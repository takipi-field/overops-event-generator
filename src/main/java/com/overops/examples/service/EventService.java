package com.overops.examples.service;

import com.overops.examples.domain.User;
import com.overops.examples.utils.EventType;

public interface EventService {

    void generateEvent(User user, boolean generateEvent, EventType eventType);
}
