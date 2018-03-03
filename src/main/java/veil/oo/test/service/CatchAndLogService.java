package veil.oo.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import veil.oo.test.domain.User;

@Service
public class CatchAndLogService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void catchAndLog(User demoUser, boolean generateEvent) {

        log.trace("user details: {}", demoUser.toString());

        if (generateEvent) {
            try {

                throw new IllegalArgumentException("fairly standard catch and log; not very helpful");

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
