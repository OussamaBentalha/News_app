package com.esgi.android.news.metier.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.esgi.android.news.physique.db.DatabaseHandler;

import java.util.List;

/**
 * Created by Sam on 09/06/16.
 */
public abstract class AbstractDAO <T> {

    private DatabaseHandler dbHandler;

    private SQLiteDatabase sqliteDb;

    public AbstractDAO(Context context) {
        this.dbHandler = new DatabaseHandler(context);
    }

    public SQLiteDatabase getSqliteDb() {
        return sqliteDb;
    }

    public SQLiteDatabase open() {
        sqliteDb = this.dbHandler.getWritableDatabase();
        return sqliteDb;
    }

    public void close() {
        sqliteDb.close();
    }

    public abstract void add(T object);
    public abstract T get(int id);
    public abstract List<T> getAll();


}
