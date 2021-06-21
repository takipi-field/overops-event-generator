package com.overops.examples.service;

import com.overops.examples.utils.TakipiAverageExecutionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggedWarnService extends AbstractEventService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @TakipiAverageExecutionTime
    @Override
    void fireEvent() {
         /*

            Warn Scenario:

            OverOps captures detailed snapshots when calls to log.warn are made.

         */

        log.warn("log.warn() called!");
    }
}
