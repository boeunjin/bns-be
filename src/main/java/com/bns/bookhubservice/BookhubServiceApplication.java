package com.bns.bookhubservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class BookhubServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookhubServiceApplication.class, args);
    }
}
