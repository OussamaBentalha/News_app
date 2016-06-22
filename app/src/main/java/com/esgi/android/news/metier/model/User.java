package com.esgi.android.news.metier.model;

/**
 * Created by Sam on 21/06/16.
 */
public class User {

    private long id;
    private String password;
    private String mail;

    public User() {
    }

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
