package com.overops.examples;

import com.overops.examples.controller.EventGenerator;
import com.overops.examples.domain.User;
import com.overops.examples.domain.UserRepository;
import com.takipi.sdk.v1.api.Takipi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;

@SpringBootApplication
public class OverOpsEventGeneratorApplication {

    private static final int STARTUP_SLEEP = 10000;
    private static final Logger log = LoggerFactory.getLogger(OverOpsEventGeneratorApplication.class);

    private final ConfigurableApplicationContext context;

    @Autowired
	private EventGenerator eventGenerator;

    public OverOpsEventGeneratorApplication(ConfigurableApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(OverOpsEventGeneratorApplication.class, args);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ApplicationRunner createUsers(UserRepository repository) {
        return (args) -> {
            repository.save(new User("George", null, "Gordon", "lord.byron@gmail.com", LocalDate.of(1751, 12, 26), "London, England", "111-23-23123", "carelesschild", "George Gordon was born in London, England, third and youngest son of Cosmo George Gordon, 3rd Duke of Gordon, and the brother of Alexander Gordon, 4th Duke of Gordon"));
            repository.save(new User("Edgar", "Allan", "Poe", "edgar@gmail.com", LocalDate.of(1809, 1, 19), "Boston, Massachusetts", "222-23-4321", "theraven", "Poe was born in Boston, the second child of two actors. His father abandoned the family in 1810, and his mother died the following year."));
            repository.save(new User("Walt", null, "Whitman", "walt.whitman@gmail.com", LocalDate.of(1819, 5, 31), "Huntington, New York", "434-12-4216", "leavesofgrass", "Born in Huntington on Long Island, Whitman worked as a journalist, a teacher, a government clerk, and—in addition to publishing his poetry—was a volunteer nurse during the American Civil War."));

            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (User user : repository.findAll()) {
                log.info(user.toString());
            }
            log.info("");

            repository.findById(1L)
                    .ifPresent(user -> {
                        log.info("User found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(user.toString());
                        log.info("");
                    });

            log.info("");
        };
    }

    @Bean
    Takipi buildTakipi() {
        return Takipi.create("OVEROPS_EVENT_GENERATOR");
    }

    @Bean
    @Profile("!test")
    public ApplicationRunner generateErrors(UserRepository repository) {
        return (args) -> {

            log.info("sleeping for {} ms before starting", STARTUP_SLEEP);

            try {
                Thread.sleep(STARTUP_SLEEP);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
    
            log.info("waking up and ready to generate errors");
    
            String eventGenId = args.containsOption("oo.eventGenId") ? args.getOptionValues("oo.eventGenId").get(0) : "RANDOM_EVENTS";
            switch (eventGenId) {
                case "MOMMY_PACKS":
                    Runnable mommyPackTest = new MommyPackTestRunnable(eventGenerator, repository.findAll().iterator().next());
                    mommyPackTest.run();
                    break;
                default: 
                    Long numEvents = args.containsOption("oo.maxNumEvents") ? Long.parseLong(args.getOptionValues("oo.maxNumEvents").get(0)) : null;
                    boolean exitOnMaxNumEvents = ((args.containsOption("oo.maxNumEvents") && args.containsOption("oo.exitOnMaxNumEvents")) ? Boolean.parseBoolean(args.getOptionValues("oo.exitOnMaxNumEvents").get(0)) : false);
                    Long randomSeed = args.containsOption("oo.randomSeed") ? Long.parseLong(args.getOptionValues("oo.randomSeed").get(0)) : null;

                    if (randomSeed != null) {
                        log.info("random seed being used is {}", randomSeed);
                    }

                    Runnable randomEventsRunner = new RandomEventsRunnable(eventGenerator, repository, randomSeed, numEvents);
                    randomEventsRunner.run();
 
                    if (exitOnMaxNumEvents)
                    {
                        System.exit(SpringApplication.exit(context));
                    }
                    break;
            }
        };
    }

}
