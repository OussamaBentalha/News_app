package com.esgi.android.news.metier.model;

/**
 * Created by Sam on 22/06/16.
 */
public interface IRefreshable {

    void refresh();

    void cancelRefresh();

}
