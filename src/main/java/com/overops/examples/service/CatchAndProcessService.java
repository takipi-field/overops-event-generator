package com.overops.examples.service;

import com.overops.examples.error.ExampleCaughtException;
import org.springframework.stereotype.Service;

@Service
public class CatchAndProcessService extends AbstractEventService {

    @Override
    void fireEvent() {
        try {
            throw new ExampleCaughtException("this exception is thrown in one method and expected to be handled in another.");
        } catch (ExampleCaughtException e) {
            log.debug("here we catch: " + e.getMessage(), e);
        }

    }
}
