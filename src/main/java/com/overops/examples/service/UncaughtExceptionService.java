package com.overops.examples.service;

import com.overops.examples.error.ExampleUncaughtException;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class UncaughtExceptionService extends AbstractEventService {


    @Override
    void fireEvent() {
        Executors.newSingleThreadExecutor(r -> new Thread(r, "uncaught-exception-thread")).execute(() -> {

            /*

                Uncaught Exception Scenario:

                This demonstrates what happens when an unexpected and uncaught exception occurs in the code.

            */

            log.debug("this thread will die with an uncaught exception");

            throw new ExampleUncaughtException("this exception is uncaught and a BIG potential problem");

        });
    }
}
