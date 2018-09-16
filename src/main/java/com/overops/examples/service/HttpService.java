package com.overops.examples.service;

import com.overops.examples.web.RestEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class HttpService extends AbstractEventService {

    @Override
    void fireEvent(boolean generateEvent) {

        String url = "http://localhost:8080/throw500";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam(RestEndpoint.GENERATE_EVENT, generateEvent);

        RestTemplate restTemplate = new RestTemplate();

        try {

            ResponseEntity<String> entity = restTemplate.getForEntity(builder.toUriString(), String.class);

            log.debug("GET call to [{}] returned this value: {}", url, entity.getBody());

        } catch (RestClientException e) {

            // log as debug because i don't want another logged error or warn

            log.debug(e.getMessage(), e);
        }
    }
}
