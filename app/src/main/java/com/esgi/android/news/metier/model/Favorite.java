package com.esgi.android.news.metier.model;

/**
 * Created by Sam on 19/07/16.
 */
public class Favorite {

    private int userId;
    private int itemId;

    public Favorite() {
    }

    public Favorite(int userId, int itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
