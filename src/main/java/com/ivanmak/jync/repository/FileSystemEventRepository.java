package com.ivanmak.jync.repository;

import com.ivanmak.jync.model.FileSystemEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ivan Makarychev on 2019-12-17.
 */
@Repository
public interface FileSystemEventRepository extends CrudRepository<FileSystemEvent, Long> {
}
