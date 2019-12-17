package com.ivanmak.jync.restservice;

import com.ivanmak.jync.model.FileSystemEvent;
import com.ivanmak.jync.repository.FileSystemEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Makarychev on 2019-10-23.
 */
@RestController
public class Controller {

    private final FileSystemEventRepository repository;

    @Autowired
    public Controller(FileSystemEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("events")
    List<FileSystemEvent> getAllEvents() {
        List<FileSystemEvent> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }
}
