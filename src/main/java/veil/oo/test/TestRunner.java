package veil.oo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import veil.oo.test.domain.User;
import veil.oo.test.controller.Controller;

import java.util.Date;
import java.util.UUID;

@Component
public class TestRunner implements ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int STARTUP_SLEEP = 20000;

    private Controller controller;

    @Autowired
    public TestRunner(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {

        long counter = 0;

        log.debug("sleeping for {} ms before starting", STARTUP_SLEEP);

        try {
            Thread.sleep(STARTUP_SLEEP);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        log.debug("waking up and ready to demo");

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


        while (true) {

            log.info("************** starting run {}", counter);

            String uuid = UUID.randomUUID().toString();

            try {
                controller.route(counter, uuid, demoUser);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }

            counter++;
        }


    }
}
