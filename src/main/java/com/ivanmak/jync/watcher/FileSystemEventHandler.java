package com.ivanmak.jync.watcher;

import com.ivanmak.jync.model.FileSystemEvent;
import org.springframework.scheduling.annotation.Async;

/**
 * Handler for FileSystemEvent
 *
 * Created by Ivan Makarychev on 2019-10-29.
 */
public interface FileSystemEventHandler {
    @Async
    void handle(FileSystemEvent event);
}
