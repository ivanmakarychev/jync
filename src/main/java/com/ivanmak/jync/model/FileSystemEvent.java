package com.ivanmak.jync.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Ivan Makarychev on 2019-10-29.
 */
@Entity
public class FileSystemEvent {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Date date;

    public FileSystemEvent() {}

    public FileSystemEvent(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
