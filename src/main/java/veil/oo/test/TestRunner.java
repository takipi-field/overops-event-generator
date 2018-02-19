package veil.oo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TestRunner implements ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int STARTUP_SLEEP = 20000;
    public static final int SHUTDOWN_SLEEP = 300000;

    private ExampleService exampleService;

    @Autowired
    public TestRunner(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        int runs = -1;

        long counter = 0;

        log.debug("sleeping for {} ms before starting", STARTUP_SLEEP);

        try {
            Thread.sleep(STARTUP_SLEEP);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        log.debug("waking up and ready to demo");

        long exceptionCounter = 0;

        User demoUser = new User();
        demoUser.setId(999999);
        demoUser.setFirstName("Tim");
        demoUser.setLastName("Veil");
        demoUser.setEmailAddress("tim.veil@overops.com");
        demoUser.setNote("interesting facts about tim...");
        demoUser.setSensitiveNote("here are some details i'd rather not share");
        demoUser.setSsn("111-22-3333");
        demoUser.setSocSecNum("111-22-3333");
        demoUser.setPassword("p@ssw0rd");
        demoUser.setLastLogin(new Date());


        while (runs == -1 || counter < runs) {

            log.debug("************** starting run {} out of {}", counter, runs);

            String uuid = UUID.randomUUID().toString();

            try {
                exampleService.fetch(counter, uuid, demoUser);
            } catch (Exception e) {
                exceptionCounter++;
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }

            counter++;
        }

        log.info("test generated {} exceptions!", exceptionCounter);

        log.debug("sleeping for {} ms before exiting", SHUTDOWN_SLEEP);

        try {
            Thread.sleep(SHUTDOWN_SLEEP);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        log.debug("bye bye");
    }
}
