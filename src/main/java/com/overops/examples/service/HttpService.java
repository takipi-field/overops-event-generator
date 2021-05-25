package com.overops.examples.service;

import com.overops.examples.web.RestEndpoint;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Service
public class HttpService extends AbstractEventService {


    @Value("${server.port:8080}")
    private int serverPort;

    @Override
    void fireEvent(boolean generateEvent) {

        String url = "http://localhost:" + serverPort + "/throwError";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam(RestEndpoint.GENERATE_EVENT, generateEvent);

        try {
            HttpClient client = HttpClientBuilder.create().build();

            String uri = builder.toUriString();

            log.debug("calling uri = {}", uri);

            HttpResponse response = client.execute(new HttpGet(uri));
            int statusCode = response.getStatusLine().getStatusCode();

            log.debug("status code = {}", statusCode);

        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        }


    }
}
