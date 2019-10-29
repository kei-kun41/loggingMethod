package fr.elouan.springboot.logging.controller;

import fr.elouan.springboot.logging.interceptor.LogOutgoingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController("/test")
public class TestLogController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public void testPost(@RequestBody String body) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.getInterceptors().add(new LogOutgoingInterceptor());
        String slip = restTemplate.getForObject("https://api.adviceslip.com/advice",String.class);

    }
}
