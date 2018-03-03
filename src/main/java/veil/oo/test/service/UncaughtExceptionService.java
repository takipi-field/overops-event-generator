package veil.oo.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import veil.oo.test.domain.User;
import veil.oo.test.error.UncaughtException;

import java.util.concurrent.Executors;

@Service
public class UncaughtExceptionService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void cantCatchMe(User demoUser, boolean generateEvent) {

        if (generateEvent) {

            Executors.newSingleThreadExecutor().execute(() -> {

                log.trace("user details: {}", demoUser.toString());

                log.debug("about to do something very dangerous and unpredictable.");

                throw new UncaughtException("this exception is uncaught and a BIG potential problem");
            });
        }
    }
}
