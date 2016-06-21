package com.esgi.android.news.metier.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.android.news.metier.model.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 21/06/16.
 */
public class UserDAO extends AbstractDAO<User> {

    private static final String TABLE_NAME = "user";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + ";";
    private static final String KEY_ID = "id";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_PASSWORD = "password";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT);";


    public UserDAO(Context context) {
        super(context);
    }

    @Override
    public void add(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MAIL, user.getMail());
        contentValues.put(KEY_PASSWORD, user.getPassword());
        getSqliteDb().insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public User get(int id) {
        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_MAIL, KEY_PASSWORD},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        User user = null;
        if (cursor != null) {
            cursor.moveToFirst();
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<User>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = getSqliteDb().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User item = new User();
                item.setId(cursor.getInt(0));
                item.setMail(cursor.getString(1));
                item.setPassword(cursor.getString(2));

                //TODO Get date

                users.add(item);
            } while (cursor.moveToNext());
        }
        return users;
    }


}
