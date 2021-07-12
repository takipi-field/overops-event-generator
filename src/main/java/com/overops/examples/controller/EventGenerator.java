package com.overops.examples.controller;

import com.overops.examples.domain.User;
import com.overops.examples.service.*;
import com.overops.examples.utils.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventGenerator
{
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
    
    private final CatchInDifferentMethodService catchInDifferentMethodService;
    
    private final SwallowInDifferentMethodService swallowInDifferentMethodService;

    @Autowired
    public EventGenerator(CatchAndProcessService catchAndProcessService, CatchAndIgnoreService catchAndIgnoreService, LoggedErrorService loggedErrorService, CustomEventService customEventService, SlowService slowService, LoggedWarnService loggedWarnService, UncaughtExceptionService uncaughtExceptionService, HttpService httpService, XmlParseService xmlParseService, CaughtExceptionDiffRouteService mommyPackService, CatchInDifferentMethodService catchInDifferentMethodService, SwallowInDifferentMethodService swallowInDifferentMethodService) {
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
        this.catchInDifferentMethodService = catchInDifferentMethodService;
        this.swallowInDifferentMethodService = swallowInDifferentMethodService;
    }

    public void generateEvent(User user, EventType eventType) {
        switch (eventType) {

            case SWALLOWED_EXCEPTION:
                catchAndIgnoreService.generateEvent(user, eventType);
                break;
            case CAUGHT_EXCEPTION:
                catchAndProcessService.generateEvent(user, eventType);
                break;
            case UNCAUGHT_EXCEPTION:
                uncaughtExceptionService.generateEvent(user, eventType);
                break;
            case LOGGED_WARNING:
                loggedWarnService.generateEvent(user, eventType);
                break;
            case LOGGED_ERROR:
                loggedErrorService.generateEvent(user, eventType);
                break;
            case TIMER:
                slowService.generateEvent(user, eventType);
                break;
            case CUSTOM_EVENT:
                customEventService.generateEvent(user, eventType);
                break;
            case HTTP_ERROR:
                httpService.generateEvent(user, eventType);
                break;
            case XML_PARSE_EXCEPTION:
				xmlParseService.generateEvent(user, eventType);
				break;
            case CAUGHT_EXCEPTION_DIFF_ROUTE:
                mommyPackService.generateEvent(user, eventType);
                break;
            case CATCH_IN_DIFFERENT_METHOD:
                catchInDifferentMethodService.generateEvent(user, eventType);
                break;
            case SWALLOW_IN_DIFFERENT_METHOD:
                swallowInDifferentMethodService.generateEvent(user, eventType);
                break;

        }
    }
}
