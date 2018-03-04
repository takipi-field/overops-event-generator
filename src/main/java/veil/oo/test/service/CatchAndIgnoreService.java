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

            /*

                Catch and Ignore Scenario:

                An exception is thrown during the normal course of a method but instead of dealing with the exception,
                the exception is buried or swallowed.  No logging occurs.  This would be invisible to logging aggregators
                like Splunk or other monitoring tools.  Usually a sign of poor code.  Can lead to serious issues.

            */

                throw new SwallowedException("Exception occurred but it was never logged, eg. swallowed exception");

            } catch (SwallowedException e) {
                // i'll just bury this one
            }
        }
    }
}
