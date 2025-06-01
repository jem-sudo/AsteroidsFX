package com.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import java.io.IOException;


@SpringBootApplication
@RestController
public class ScoreMicroserviceApplication {
    private int score = 0;
    public static void main(String[] args) {
        SpringApplication.run(ScoreMicroserviceApplication.class, args);
    }
 
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "mvn", "exec:exec");
        processBuilder.inheritIO();
        processBuilder.start();
    }
    
    @GetMapping(value = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public int getScore() {
        System.out.println("Current score: " + score);
        return score;
    }

    @PostMapping(value = "/score/increment", produces = MediaType.APPLICATION_JSON_VALUE)
    public int incrementScore() {
        score++;
        System.out.println("Score incremented by 1: " + score);
        return score;
    }
} 

