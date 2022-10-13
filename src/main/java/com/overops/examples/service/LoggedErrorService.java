package com.overops.examples.service;

import org.springframework.stereotype.Service;

@Service
public class LoggedErrorService extends AbstractEventService {


    @Override
    public void fireEvent() {
        /*

            Error Scenario:

            OverOps captures detailed snapshots when calls to log.error are made.

        */

        log.error("log.error() called!");
    }
}
