package veil.oo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TestRunner implements ApplicationRunner {

    public static final int RUNS = 100;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BrokenService brokenService;

    @Autowired
    public TestRunner(BrokenService brokenService) {
        this.brokenService = brokenService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        long counter = 0;

        log.debug("sleeping");

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        log.debug("waking up");

        long blowupCounter = 0;

        while (counter < RUNS) {

            log.debug("************** starting run {} out of {}", counter, RUNS);

            String uuid = UUID.randomUUID().toString();

            try {
                brokenService.doBadStuff(counter, uuid);
            } catch (BadStuffHappened badStuffHappened) {
                log.error(badStuffHappened.getMessage(), badStuffHappened);
            } catch (Exception e) {

                blowupCounter++;

                log.debug("this is blowup {}", blowupCounter);

                // tiny links will always print because this is logged thru a framework
                log.error("uuid {" + uuid + "} - something happened i wasn't expecting ;)...", e);

                // tiny links will only be printed if "-Dtakipi.etl" is set at JVM startup or its set in the gui (under installation keys)
                //e.printStackTrace();
                //System.out.println(e.getMessage());
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }

            counter++;
        }

        log.debug("app blew up {} times!", blowupCounter);
    }
}
