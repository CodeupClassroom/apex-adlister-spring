package com.codeup.blog.models;

import javax.persistence.*;

// ORM

@Entity
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToOne
    private User owner;

    // spring framework uses this empty constructor
    public Ad() {}

    // insert
    public Ad(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.owner = user;
    }

    // read
    public Ad(long id, String title, String description, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
