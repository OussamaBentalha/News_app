package com.esgi.android.news.physique.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.android.news.metier.enumeration.EnumNewspaper;
import com.esgi.android.news.metier.model.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sam on 09/06/16.
 */
public class ItemDAO extends AbstractDAO <Item>{

    private static final String TABLE_NAME = "item";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + ";";
    private static final String KEY_ID = "id";
    private static final String KEY_MAGAZINE = "magazine";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL_LINK = "url_link";
    private static final String KEY_URL_IMAGE = "url_image";
    private static final String KEY_PUB_DATE = "pub_date";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MAGAZINE + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_URL_LINK + " TEXT, "
            + KEY_URL_IMAGE + " TEXT, "
            + KEY_PUB_DATE + " DATE);";

    public ItemDAO(Context context) {
        super(context);
    }

    @Override
    public void add(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MAGAZINE, item.getMagazine());
        contentValues.put(KEY_TITLE, item.getTitle());
        contentValues.put(KEY_DESCRIPTION, item.getDescription());
        contentValues.put(KEY_URL_LINK, item.getUrlItem());
        contentValues.put(KEY_URL_IMAGE, item.getUrlPicture());
        //date
        if(item.getDate() != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            contentValues.put(KEY_PUB_DATE, dateFormat.format(item.getDate()));
        }
        getSqliteDb().insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public Item get(int id) {
        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_MAGAZINE, KEY_TITLE, KEY_DESCRIPTION, KEY_URL_IMAGE, KEY_PUB_DATE, KEY_URL_LINK},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        Item item = null;
        if (cursor != null) {
            cursor.moveToFirst();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pubDate = null;
            //TODO
            /*try {
                pubDate = dateFormat.parse(cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

            item = new Item(//cursor.getInt(0),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    pubDate,
                    cursor.getString(6));
            item.setMagazine(cursor.getString(1));
        }
        return item;
    }

    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = getSqliteDb().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getLong(0));
                item.setMagazine(cursor.getString(1));
                item.setTitle(cursor.getString(2));
                item.setDescription(cursor.getString(3));
                item.setUrlItem(cursor.getString(4));
                item.setUrlPicture(cursor.getString(5));

                //TODO Get date

                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public List<Item> getAll(EnumNewspaper enumNewspaper) {
        List<Item> items = new ArrayList<>();

        //String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_MAGAZINE + "=" + enumNewspaper.name();
        //Cursor cursor = getSqliteDb().rawQuery(selectQuery, null);

        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_MAGAZINE, KEY_TITLE, KEY_DESCRIPTION, KEY_URL_IMAGE, KEY_PUB_DATE, KEY_URL_LINK},
                KEY_MAGAZINE + "=?",
                new String[]{enumNewspaper.name()},
                null,
                null,
                null,
                null
        );


        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getLong(0));
                item.setMagazine(cursor.getString(1));
                item.setTitle(cursor.getString(2));
                item.setDescription(cursor.getString(3));
                item.setUrlItem(cursor.getString(4));
                item.setUrlPicture(cursor.getString(5));

                //TODO Get date

                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }



}
