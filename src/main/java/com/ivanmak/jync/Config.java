package com.ivanmak.jync;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

/**
 * Created by Ivan Makarychev on 2019-10-29.
 */
@Configuration
@EnableAsync
public class Config {

    @Value("${sync.source}")
    private String source;

    @Value("${sync.destination}")
    private String destination;

    @Value("${sync.shell.user}")
    private String user;

    @Value("#{'${sync.servers}'.split(',')}")
    private List<String> servers;

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getUser() {
        return user;
    }

    public List<String> getServers() {
        return servers;
    }
}
