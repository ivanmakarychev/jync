package com.ivanmak.jync.watcher;

import com.ivanmak.jync.Config;
import com.ivanmak.jync.model.FileSystemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Watches changes in specified directory
 *
 * Created by Ivan Makarychev on 2019-10-29.
 */
@Component
public class Watcher implements Runnable {

    private final WatchService watcher;
    private final Config config;
    private final Logger log;
    private final List<FileSystemEventHandler> listeners;

    @Autowired
    public Watcher(Config config, List<FileSystemEventHandler> listeners) throws IOException {
        this.config = config;
        log = LoggerFactory.getLogger(this.getClass());
        watcher = FileSystems.getDefault().newWatchService();
        this.listeners = listeners;
    }

    @PostConstruct
    private void init() throws IOException {
        Path source = Paths.get(config.getSource());
        source.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
        new Thread(this, "watcher").start();
        log.debug("Watcher thread started");
    }

    @Override
    public void run() {
        log.info("Starting watching");
        while (true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException e) {
                log.error("Watcher thread has been interrupted and will stop", e);
                return;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // This key is registered only
                // for ENTRY_CREATE and ENTRY_MODIFY events,
                // but an OVERFLOW event can
                // occur regardless if events
                // are lost or discarded.
                if (kind == OVERFLOW) {
                    log.info("Overflow occured");
                    continue;
                }

                WatchEvent<Path> ev = (WatchEvent<Path>)event;
                Path filename = ev.context();
                log.info("Got filename: " + filename);
                FileSystemEvent event1 = new FileSystemEvent(filename.getFileName().toString(), new Date());
                listeners.forEach(listener -> listener.handle(event1));
                log.info("All listeners notified");
            }

            boolean valid = key.reset();
            if (!valid) {
                log.error("Source directory is inaccessible. Watcher thread will stop");
                return;
            }
        }
    }
}
