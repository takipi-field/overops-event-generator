package com.overops.examples.service;

import com.overops.examples.error.ExampleSwallowedException;
import com.overops.examples.utils.TakipiInvocationCounter;
import org.springframework.stereotype.Service;

@Service
public class CatchAndIgnoreService extends AbstractEventService {

    @TakipiInvocationCounter
    @Override
    void fireEvent() {

        try {

            /*

                Catch and Ignore Scenario:

                An exception is thrown during the normal course of a method but instead of dealing with the exception,
                the exception is buried or swallowed.  No logging occurs.  This would be invisible to logging aggregators
                like Splunk or other monitoring tools.  Usually a sign of poor code.  Can lead to serious issues.

            */

            throw new ExampleSwallowedException("Exception occurred but it was never logged, eg. swallowed exception");

        } catch (ExampleSwallowedException e) {
            // i'll just bury this one
        }
    }
}
