package com.overops.examples.service;

import com.overops.examples.error.ExampleUncaughtException;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class UncaughtExceptionService extends AbstractEventService {


    @Override
    void fireEvent(boolean generateEvent) {

        if (!generateEvent) {
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {

            /*

                Uncaught Exception Scenario:

                This demonstrates what happens when an unexpected and uncaught exception occurs in the code.

            */

            throw new ExampleUncaughtException("this exception is uncaught and a BIG potential problem");

        });
    }
}
