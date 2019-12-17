package com.ivanmak.jync.repository;

import com.ivanmak.jync.model.FileSystemEvent;
import com.ivanmak.jync.watcher.FileSystemEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivan Makarychev on 2019-12-17.
 */
@Service
public class PersistingFileSystemEventHandler implements FileSystemEventHandler {

    private final FileSystemEventRepository repository;

    @Autowired
    public PersistingFileSystemEventHandler(FileSystemEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(FileSystemEvent event) {
        repository.save(event);
    }
}
