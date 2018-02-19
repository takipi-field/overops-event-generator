package veil.oo.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import veil.oo.test.domain.User;
import veil.oo.test.error.SwallowedException;

@Service
public class CatchAndIgnoreService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void catchAndIgnore(User demoUser, boolean generateEvent) {

        log.trace("user details: {}", demoUser.toString());

        if (generateEvent) {
            try {

                throw new SwallowedException("Exception occurred but it was never logged, eg. swallowed exception");

            } catch (SwallowedException e) {
                // i'll just bury this one
            }
        }
    }
}
