package com.ems.springcloud;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        char a = 'H';
        int b = a;
        System.out.println(b);
    }
}

