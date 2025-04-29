package com.home.connections;

import com.home.connections.listener.BotEventListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.home.connections")
public class ConnectionsApplication {


    public static void main(String[] args) {
        SpringApplication.run(ConnectionsApplication.class, args);

    }

}
