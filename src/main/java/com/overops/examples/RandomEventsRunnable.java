package com.overops.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import com.overops.examples.controller.EventGenerator;
import com.overops.examples.domain.User;
import com.overops.examples.domain.UserRepository;
import com.overops.examples.utils.EventType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RandomEventsRunnable extends AbstractEventsRunnable {
    private static final Logger log = LoggerFactory.getLogger(RandomEventsRunnable.class);

    UserRepository userRepository;
    Random random;
    Long numEvents;
    final EventCallable1 eventCallable1;
    final EventCallable2 eventCallable2;

    RandomEventsRunnable(EventGenerator eventGenerator, UserRepository repository, Long randomSeed, Long numEvents) {
        super(eventGenerator, Executors.newFixedThreadPool(5));
        this.userRepository = repository;
        this.random = (randomSeed == null ? new Random() : new Random(randomSeed));
        this.numEvents = numEvents;
        eventCallable1 = new EventCallable1(eventGenerator);
        eventCallable2 = new EventCallable2(eventGenerator);
    }

    @Override
    public void run() {
        int userCount = (int) userRepository.count();
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        users.sort((u1, u2)->Long.valueOf(u1.getId()).compareTo(u2.getId()));

        AtomicLong invocationCounter = new AtomicLong(0);
        AtomicLong eventCounter = new AtomicLong(0);

        while ((numEvents == null) || (numEvents < 0) || (eventCounter.get() < numEvents)) {
            User user = users.get(random.nextInt(userCount));
            EventCallable callable = random.nextBoolean() ? eventCallable1 : eventCallable2;
            EventType event = EventType.randomEvent(random);

            boolean eventGenerated = generateEvent(user, event, callable);
            if (eventGenerated) {
                eventCounter.incrementAndGet();
            }
            invocationCounter.incrementAndGet();
        }

        log.info("event generator finished!!!!  ran {} times and generated {} events.", invocationCounter.get(), eventCounter.get());
};
    
}
