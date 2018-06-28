package com.overops.examples.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.overops.examples.domain.User;

@Service
public class LoggedErrorService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void errorsAbound(User demoUser, boolean generateEvent) {

        log.trace("user details: {}", demoUser.toString());

        if (generateEvent) {

             /*

                Error Scenario:

                OverOps captures detailed snapshots when calls to log.error are made.

             */

            log.error("log.error() called!");
        }
    }
}
