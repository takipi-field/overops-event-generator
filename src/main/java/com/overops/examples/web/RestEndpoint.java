package com.overops.examples.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RestEndpoint {

    public static final String GENERATE_EVENT = "generateEvent";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = "/throwError", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void throwError(HttpServletResponse response) throws IOException {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        log.debug("setting HttpStatus = {}", internalServerError);

        response.sendError(internalServerError.value());

    }

}
