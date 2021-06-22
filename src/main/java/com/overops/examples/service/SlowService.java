package com.overops.examples.service;

import org.springframework.stereotype.Service;

@Service
public class SlowService extends AbstractEventService {


    @Override
    void fireEvent() {
        /*

            Timer Scenario:

            OverOps can be configured to capture events when method duration exceeds a user defined threshold

        */

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
