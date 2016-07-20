package com.esgi.android.news.physique.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.android.news.metier.model.Favorite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 19/07/16.
 */
public class FavoriteDAO extends AbstractDAO<Favorite> {

    public static final String TABLE_NAME = "favorite";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + ";";
    public static final String KEY_ID = "id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_ITEM_ID = "item_id";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_ID + " INTEGER, "
            + KEY_ITEM_ID + " INTEGER); ";

    public FavoriteDAO(Context context) {
        super(context);
    }

    @Override
    public void add(Favorite object) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_ID, object.getUserId());
        contentValues.put(KEY_ITEM_ID, object.getItemId());
        getSqliteDb().insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public Favorite get(int id) {
        return null;
    }

    public List<Favorite> getByUser(int userId) {
        List<Favorite> favorites = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_USER_ID + "=" + String.valueOf(userId);
        Cursor cursor = getSqliteDb().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Favorite fav = new Favorite(cursor.getInt(1), cursor.getInt(1));
                favorites.add(fav);
            } while (cursor.moveToNext());
        }
        return favorites;
    }

    public void remove(Favorite favorite){
        //String selectQuery = "DELETE FROM " + TABLE_NAME
          //      + " WHERE " + KEY_USER_ID + "=" + favorite.getUserId()
          //      + " AND " + KEY_ITEM_ID + "=" + favorite.getItemId();
        //getSqliteDb().rawQuery(selectQuery, null);

        String deleteQuery = KEY_USER_ID + "=?"  + " AND " + KEY_ITEM_ID + "=?";
        getSqliteDb().delete(TABLE_NAME, deleteQuery, new String[]{String.valueOf(favorite.getUserId()), String.valueOf(favorite.getItemId())});


    }

    public boolean isFavorite(Favorite favorite){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME
                + " WHERE " + KEY_USER_ID + "=" + favorite.getUserId()
                + " AND " + KEY_ITEM_ID + "=" + favorite.getItemId();

        Cursor cursor = getSqliteDb().rawQuery(selectQuery, null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                Favorite fav = new Favorite(cursor.getInt(1), cursor.getInt(2));
                if(fav != null){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Favorite> getAll() {
        return null;
    }
}
