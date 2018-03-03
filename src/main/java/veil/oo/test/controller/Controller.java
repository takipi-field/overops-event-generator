package veil.oo.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import veil.oo.test.domain.User;
import veil.oo.test.service.*;

import java.util.Random;

@Component
public class Controller {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Random rand = new Random();

    private final BubbleService bubbleService;

    private final CatchAndIgnoreService catchAndIgnoreService;

    private final CatchAndLogService catchAndLogService;

    private final CustomEventService customEventService;

    private final SlowService slowService;

    private final WarningService warningService;

    private final VeryBrokenService veryBrokenService;

    private final UncaughtExceptionService uncaughtExceptionService;


    @Autowired
    public Controller(BubbleService bubbleService, CatchAndIgnoreService catchAndIgnoreService, CatchAndLogService catchAndLogService, CustomEventService customEventService, SlowService slowService, WarningService warningService, VeryBrokenService veryBrokenService, UncaughtExceptionService uncaughtExceptionService) {
        this.bubbleService = bubbleService;
        this.catchAndIgnoreService = catchAndIgnoreService;
        this.catchAndLogService = catchAndLogService;
        this.customEventService = customEventService;
        this.slowService = slowService;
        this.warningService = warningService;
        this.veryBrokenService = veryBrokenService;
        this.uncaughtExceptionService = uncaughtExceptionService;
    }

    public void route(long counter, String uuid, User demoUser) {

        log.trace("uuid [{}] - counter is {}", uuid, counter);

        boolean generateEvent = false;

        if (counter != 0 && counter % 5 == 0) {
            generateEvent = true;
        }

        log.info("fetching info for user: {}", demoUser.toString());

        int scenario = rand.nextInt(7) + 1;

        log.debug("event scenario: {}", scenario);

        if (scenario == 1) {

            bubbleService.bubbleException(demoUser, generateEvent);

        } else if (scenario == 2) {

            catchAndIgnoreService.catchAndIgnore(demoUser, generateEvent);

        } else if (scenario == 3) {

            warningService.warningsAbound(demoUser, generateEvent);

        } else if (scenario == 4) {

            slowService.longRunningMethod(demoUser, generateEvent);

        } else if (scenario == 5) {

            customEventService.fireCustomEvent(demoUser, generateEvent);

        } else if (scenario == 6) {

            catchAndLogService.catchAndLog(demoUser, generateEvent);

        } else if (scenario == 7) {

            uncaughtExceptionService.cantCatchMe(demoUser, generateEvent);

        } else {

            log.warn("invalid scenario: {}", scenario);

        }

        if (counter == 0 || counter % 10 != 0) {
            try {
                veryBrokenService.execute(demoUser, true);
            } catch (UnsupportedOperationException e) {
                // need to investigate why this method always fails
            }
        }

    }

}
