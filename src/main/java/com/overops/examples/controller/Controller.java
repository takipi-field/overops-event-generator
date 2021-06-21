package com.overops.examples.controller;

import com.overops.examples.domain.User;
import com.overops.examples.service.*;
import com.overops.examples.utils.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Controller
{
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private final CatchAndProcessService catchAndProcessService;

    private final CatchAndIgnoreService catchAndIgnoreService;

    private final LoggedErrorService loggedErrorService;

    private final CustomEventService customEventService;

    private final SlowService slowService;

    private final LoggedWarnService loggedWarnService;

    private final UncaughtExceptionService uncaughtExceptionService;

    private final HttpService httpService;

    private final XmlParseService xmlParseService;

    private final CaughtExceptionDiffRouteService mommyPackService;

    private Random random = new Random();


    @Autowired
    public Controller(CatchAndProcessService catchAndProcessService, CatchAndIgnoreService catchAndIgnoreService, LoggedErrorService loggedErrorService, CustomEventService customEventService, SlowService slowService, LoggedWarnService loggedWarnService, UncaughtExceptionService uncaughtExceptionService, HttpService httpService, XmlParseService xmlParseService, CaughtExceptionDiffRouteService mommyPackService) {
        this.catchAndProcessService = catchAndProcessService;
        this.catchAndIgnoreService = catchAndIgnoreService;
        this.loggedErrorService = loggedErrorService;
        this.customEventService = customEventService;
        this.slowService = slowService;
        this.loggedWarnService = loggedWarnService;
        this.uncaughtExceptionService = uncaughtExceptionService;
        this.httpService = httpService;
        this.xmlParseService = xmlParseService;
        this.mommyPackService = mommyPackService;
    }

    public void route(User user) {

        EventType event = EventType.randomEvent(random);

        log.trace("generate event for type [{}]", event);

        switch (event) {

            case SWALLOWED_EXCEPTION:
                catchAndIgnoreService.generateEvent(user, event);
                break;
            case CAUGHT_EXCEPTION:
                catchAndProcessService.generateEvent(user, event);
                break;
            case UNCAUGHT_EXCEPTION:
                uncaughtExceptionService.generateEvent(user, event);
                break;
            case LOGGED_WARNING:
                loggedWarnService.generateEvent(user, event);
                break;
            case LOGGED_ERROR:
                loggedErrorService.generateEvent(user, event);
                break;
            case TIMER:
                slowService.generateEvent(user, event);
                break;
            case CUSTOM_EVENT:
                customEventService.generateEvent(user, event);
                break;
            case HTTP_ERROR:
                httpService.generateEvent(user, event);
                break;
            case XML_PARSE_EXCEPTION:
				xmlParseService.generateEvent(user, event);
				break;
            case CAUGHT_EXCEPTION_DIFF_ROUTE:
                mommyPackService.generateEvent(user, event);
                break;

        }
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Random getRandom() {
        return random;
    }
}
