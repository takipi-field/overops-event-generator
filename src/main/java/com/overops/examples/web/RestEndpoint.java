package com.overops.examples.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestEndpoint {

    public static final String GENERATE_EVENT = "generateEvent";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = "/throw500", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> throw500(@RequestParam(value = GENERATE_EVENT, required = false, defaultValue = "false") boolean generateEvent) {

        if (generateEvent) {
            log.debug("setting HttpStatus = {}", HttpStatus.INTERNAL_SERVER_ERROR);

            return new ResponseEntity<>("error generated", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
