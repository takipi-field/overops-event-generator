package veil.oo.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import veil.oo.test.domain.User;

@Service
public class BubbleService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void bubbleException(User demoUser, boolean generateEvent) {

        log.trace("user details: {}", demoUser.toString());

        if (generateEvent) {
            throw new NullPointerException("a value i thought was not null, was.  Ouch :(");
        }
    }

}
