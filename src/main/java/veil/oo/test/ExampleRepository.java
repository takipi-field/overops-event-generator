package veil.oo.test;

import com.takipi.sdk.v1.api.Takipi;
import com.takipi.sdk.v1.api.core.events.TakipiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ExampleRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Random rand = new Random();


    private static Takipi takipi = Takipi.create("example instance");


    public void getData(boolean generateException, User demoUser) {

        log.info("fetching info for user: {}", demoUser.toString());

        if (generateException) {

            int number = rand.nextInt(6) + 1;

            log.debug("random number is {}", number);

            if (number == 1) {

                // testing a basic error condition

                throw new NullPointerException("a value i thought was not null, was.  ouch.");

            } else if (number == 2) {

                try {

                    if (number != 3) {
                        throw new SwallowedException("2 does not equal 3 - this was buried");
                    }

                } catch (SwallowedException e) {
                    // i'll just bury this one
                }

            } else if (number == 3) {

                if (number != 4) {
                    log.warn("warning!!!! - this number is not what i thought it would be and so was logged");
                }

            } else if (number == 4) {

                log.info("i'm concerned this method could take too long to execute when number = {}", number);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }

            } else if (number == 5) {

                TakipiEvent customEvent = takipi.events().createEvent("Important Custom Event");

                log.info("firing custom event because this an important point in my code");

                customEvent.fire();

            } else if (number == 6) {

                try {

                    if (true) {
                        throw new IllegalArgumentException("fairly standard catch and log; not very helpful");
                    }

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                log.warn("got an unexpected number {}", number);
            }

        } else {
            log.info("successfully fetched info for user: {}", demoUser.toString());
        }

    }
}
