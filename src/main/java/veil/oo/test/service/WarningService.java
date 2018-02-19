package veil.oo.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import veil.oo.test.domain.User;

@Service
public class WarningService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void warningsAbound(User demoUser, boolean generateEvent) {

        log.trace("user details: {}", demoUser.toString());

        if (generateEvent) {
            log.warn("warning!!!! - this number is not what i thought it would be and so was logged");
        }
    }
}
