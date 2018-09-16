package com.overops.examples.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RestEndpoint {

    public static final String GENERATE_EVENT = "generateEvent";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = "/throw500", method = RequestMethod.GET)
    public ResponseEntity throw500(HttpServletRequest request, HttpServletResponse response) {

        String generateEvent = request.getParameter(GENERATE_EVENT);

        if (generateEvent != null && generateEvent.equalsIgnoreCase(Boolean.TRUE.toString())) {
            log.debug("going to call response.sendError() with {}", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
