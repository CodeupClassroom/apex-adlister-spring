package com.codeup.blog.models;


import javax.persistence.*;

@Entity
@Table(name = "ad_image")
public class AdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    public AdImage() {
    }

    public AdImage(String path, String description, Ad ad) {
        this.path = path;
        this.description = description;
        this.ad = ad;
    }

    public AdImage(long id, String path, String description, Ad ad) {
        this.id = id;
        this.path = path;
        this.description = description;
        this.ad = ad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
