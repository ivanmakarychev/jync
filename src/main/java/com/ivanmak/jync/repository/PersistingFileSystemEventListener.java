package com.ivanmak.jync.repository;

import com.ivanmak.jync.model.FileSystemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan Makarychev on 2019-12-17.
 */
@Component
public class PersistingFileSystemEventListener {

    private final FileSystemEventRepository repository;
    private final Logger log = LoggerFactory.getLogger(PersistingFileSystemEventListener.class);

    @Autowired
    public PersistingFileSystemEventListener(FileSystemEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void handle(FileSystemEvent change) {
        repository.save(change);
    }
}
