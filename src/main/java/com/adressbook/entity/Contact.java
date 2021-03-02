package com.adressbook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name="adressbook")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Email")
    private String email;
    @JsonProperty(value = "Image")
    private Blob image;

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public Blob getImage() {
        return image;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setImage(Blob image) {
        this.image = image;
    }
}
