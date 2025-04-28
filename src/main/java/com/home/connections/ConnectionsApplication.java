package com.home.connections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.home.connections")
public class ConnectionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectionsApplication.class, args);
    }

}
