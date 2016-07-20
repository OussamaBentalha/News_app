package com.esgi.android.news.physique.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esgi.android.news.physique.db.dao.FavoriteDAO;
import com.esgi.android.news.physique.db.dao.ItemDAO;
import com.esgi.android.news.physique.db.dao.UserDAO;

/**
 * Created by Sam on 09/06/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.CREATE_TABLE);
        db.execSQL(ItemDAO.CREATE_TABLE);
        db.execSQL(FavoriteDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
