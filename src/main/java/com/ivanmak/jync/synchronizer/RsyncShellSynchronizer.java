package com.ivanmak.jync.synchronizer;

import com.ivanmak.jync.Config;
import com.ivanmak.jync.model.FileSystemEvent;
import com.ivanmak.jync.watcher.FileSystemEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Synchronizes directories using rsync shell command
 *
 * Created by Ivan Makarychev on 2019-10-23.
 */
@Service
class RsyncShellSynchronizer implements FileSystemEventHandler {

    private final Config config;
    private final Logger log;
    private List<ProcessBuilder> processBuilderList;

    @Autowired
    RsyncShellSynchronizer(Config config) {
        this.config = config;
        log = LoggerFactory.getLogger(getClass().getName());
    }

    @PostConstruct
    private void init() {
        List<String> servers = config.getServers();
        processBuilderList = new ArrayList<>(servers.size());
        for (String server : servers) {
            ProcessBuilder processBuilder;
            if ("localhost".equals(server)) {
                processBuilder = new ProcessBuilder("rsync", "-r", config.getSource(), config.getDestination());
                log.info("Command: " + processBuilder.command());
            } else {
                processBuilder = new ProcessBuilder("rsync", "-r", config.getSource(),
                        String.format("%s@%s:%s", config.getUser(), server, config.getDestination()));
            }
            processBuilderList.add(processBuilder);
        }
    }

    @Override
    public void handle(FileSystemEvent event) {
        log.info("Start synchronizing");
        processBuilderList.forEach(pb -> {
            try {
                Process p = pb.start();
                int exitCode = p.waitFor();
                if (exitCode != 0) {
                    log.error("Execution of {} failed", pb.command());
                }
            } catch (IOException e) {
                log.error("Failed to start command", e);
            } catch (InterruptedException e) {
                log.error("Command execution has not been finished", e);
            }
        });
        log.info("Synchronization finished for file " + event.getName());
    }
}
