package com.esgi.android.news.metier.model;

import java.util.Date;

/**
 * Created by Sam on 02/05/16.
 */
public class Item {

    private long id;
    private String magazine;
    private String title;
    private String description;
    private String urlPicture;
    private Date date;
    private String urlItem;

    public Item(){
    }

    public Item(String title, String description, String urlPicture, Date date, String urlItem) {
        this.title = title;
        this.description = description;
        this.urlPicture = urlPicture;
        this.date = date;
        this.urlItem = urlItem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrlItem() {
        return urlItem;
    }

    public void setUrlItem(String urlItem) {
        this.urlItem = urlItem;
    }
}
