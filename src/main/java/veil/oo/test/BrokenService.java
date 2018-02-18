package veil.oo.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Service
public class BrokenService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public String doBadStuff(long counter, String uuid) throws BadStuffHappened {

        log.trace("uuid {" + uuid + "} - creating HttpGet");
        HttpGet httpGet = new HttpGet("http://jdbc-binary.hdp.local:8080");


        String result;

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

            log.info("uuid {" + uuid + "} - response status line: " + response.getStatusLine());

            HttpEntity entity = response.getEntity();

            try (InputStream content = entity.getContent();
                 Scanner scanner = new Scanner(content).useDelimiter("\\A")) {

                result = scanner.hasNext() ? scanner.next() : "";

                log.debug("uuid {" + uuid + "} - response content as string: [" + result + "]");
            }


            if (counter != 0 && counter % 5 == 0) {
                log.warn("uuid {" + uuid + "} - about to generate a null pointer for fun!");
                throw new NullPointerException();
            }


            return result;
        } catch (IOException io) {
            throw new BadStuffHappened(io);
        }


    }

}
