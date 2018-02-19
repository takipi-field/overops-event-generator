package veil.oo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExampleRepository generator;


    public void fetch(long counter, String uuid, User demoUser){

        log.trace("uuid [{}] - counter is {}", uuid, counter);

        boolean generateException = false;

        if (counter != 0 && counter % 5 == 0) {
            generateException = true;

        }

        generator.getData(generateException, demoUser);
    }

}
