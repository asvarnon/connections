package com.home.connections;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConnectionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectionsApplication.class, args);
    }

}
