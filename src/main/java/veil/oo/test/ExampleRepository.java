package veil.oo.test;

import com.takipi.sdk.v1.api.Takipi;
import com.takipi.sdk.v1.api.core.events.TakipiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static sun.java2d.loops.SurfaceType.Custom;

@Component
public class ExampleRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Random rand = new Random();

    private static final List<Integer> GIVEN_LIST = Arrays.asList(1, 2, 3, 4, 5, 6);

    private static Takipi takipi = Takipi.create("example instance");


    public void getData(boolean generateException, User demoUser) {

        log.info("fetching info for user: {}", demoUser);

        if (generateException) {

            int number = GIVEN_LIST.get(rand.nextInt(GIVEN_LIST.size()));

            log.debug("number is {}", number);

            if (number == 1) {

                // testing a basic error condition

                String test = "foo";

                if (test != null) {
                    test = null;
                }

                test.equals("bar");

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

                log.debug("i'm concerned this method could take too long to execute");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }

            } else if (number == 5) {

                TakipiEvent customEvent = takipi.events().createEvent("My Custom Event");

                log.debug("firing custom event because this an important point in my code");

                customEvent.fire();

            } else if (number == 6) {

                try {

                    if (true) {
                        throw new IllegalArgumentException("fairly standard catch and log; not very helpful");
                    }

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }

        } else {
            log.debug("method finished successfully");
        }

    }
}
