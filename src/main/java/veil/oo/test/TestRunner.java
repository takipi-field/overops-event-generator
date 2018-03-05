package veil.oo.test;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import veil.oo.test.controller.Controller;
import veil.oo.test.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class TestRunner implements ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String USERS_CSV = "/users.csv";

    private static final int STARTUP_SLEEP = 20000;

    private final Controller controller;

    private final Random random = new Random();

    @Autowired
    public TestRunner(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {

        long counter = 0;

        try {
            List<User> users = loadUsers(User.class, USERS_CSV);

            int size = users.size();

            log.info("loaded {} users", size);

            log.info("sleeping for {} ms before starting", STARTUP_SLEEP);

            try {
                Thread.sleep(STARTUP_SLEEP);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }

            log.info("waking up and ready to demo");

            while (true) {

                log.info("************** starting run {}", counter);

                String uuid = UUID.randomUUID().toString();

                User user = users.get(random.nextInt(size));

                try {
                    controller.route(counter, uuid, user);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }

                counter++;
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private <T> List<T> loadUsers(Class<T> type, String fileName) throws IOException {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        try (InputStream file = new ClassPathResource(fileName, this.getClass()).getInputStream()) {
            MappingIterator<T> readValues = mapper.readerFor(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        }
    }
}
