package com.example.demo;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringMapMessage;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

    private static final Logger logger = LogManager.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    public static class MyRest {
        @GetMapping
        String get(String request) throws UnknownHostException, InterruptedException {
            logger.info("Get: " + request);
            Thread.sleep(ThreadLocalRandom.current().nextInt(100,800));
            return "Hello from " + InetAddress.getLocalHost().getHostName();
        }

        @GetMapping("/error")
        ResponseEntity<Void> error() {
            logger.error("error!!!");
            throw new RuntimeException("error");
        }
    }
}
