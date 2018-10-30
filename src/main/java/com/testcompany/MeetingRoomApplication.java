package com.testcompany;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value ={
        "com.testcompany.controllers",
        "com.testcompany.store",
        "com.testcompany.config"
})
public class MeetingRoomApplication {

    public static void main(String[] args){
        SpringApplication.run(MeetingRoomApplication.class, args);

    }
}
