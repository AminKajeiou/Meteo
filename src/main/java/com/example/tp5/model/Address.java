package com.example.tp5.model;


import javax.persistence.*;
import java.sql.Date;


@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private Date creation;
    private String content;
    private String auteur;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", creation=" + creation +
                ", content='" + content + '\'' +
                ", auteur='" + auteur + '\'' +
                '}';
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
