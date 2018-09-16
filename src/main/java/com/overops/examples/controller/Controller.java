package com.overops.examples.controller;

import com.overops.examples.domain.User;
import com.overops.examples.service.*;
import com.overops.examples.utils.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private final CatchAndProcessService catchAndProcessService;

    private final CatchAndIgnoreService catchAndIgnoreService;

    private final LoggedErrorService loggedErrorService;

    private final CustomEventService customEventService;

    private final SlowService slowService;

    private final LoggedWarnService loggedWarnService;

    private final UncaughtExceptionService uncaughtExceptionService;

    private final HttpService httpService;


    @Autowired
    public Controller(CatchAndProcessService catchAndProcessService, CatchAndIgnoreService catchAndIgnoreService, LoggedErrorService loggedErrorService, CustomEventService customEventService, SlowService slowService, LoggedWarnService loggedWarnService, UncaughtExceptionService uncaughtExceptionService, HttpService httpService) {
        this.catchAndProcessService = catchAndProcessService;
        this.catchAndIgnoreService = catchAndIgnoreService;
        this.loggedErrorService = loggedErrorService;
        this.customEventService = customEventService;
        this.slowService = slowService;
        this.loggedWarnService = loggedWarnService;
        this.uncaughtExceptionService = uncaughtExceptionService;
        this.httpService = httpService;
    }

    public boolean route(long counter, User user) {

        boolean generateEvent = false;

        if (counter != 0 && counter % 5 == 0) {
            generateEvent = true;
        }

        EventType event = EventType.randomEvent();

        log.trace("for run {}, generate event for type [{}]? {}", counter, event, generateEvent);

        switch (event) {

            case SWALLOWED_EXCEPTION:
                catchAndIgnoreService.generateEvent(user, generateEvent, event);
                break;
            case CAUGHT_EXCEPTION:
                catchAndProcessService.generateEvent(user, generateEvent, event);
                break;
            case UNCAUGHT_EXCEPTION:
                uncaughtExceptionService.generateEvent(user, generateEvent, event);
                break;
            case LOGGED_WARNING:
                loggedWarnService.generateEvent(user, generateEvent, event);
                break;
            case LOGGED_ERROR:
                loggedErrorService.generateEvent(user, generateEvent, event);
                break;
            case TIMER:
                slowService.generateEvent(user, generateEvent, event);
                break;
            case CUSTOM_EVENT:
                customEventService.generateEvent(user, generateEvent, event);
                break;
            case HTTP_ERROR:
                httpService.generateEvent(user, generateEvent, event);
                break;
        }


        return generateEvent;


    }

}
