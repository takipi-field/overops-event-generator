package veil.oo.test.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RestEndpoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/throw500")
    @ResponseBody
    public String throw500(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "an error 500 occurred in the webservice");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return "I threw an error";
    }

    @GetMapping("/throw404")
    @ResponseBody
    public String throw404(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "a 404 occurred");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return "i was not found";
    }


}
