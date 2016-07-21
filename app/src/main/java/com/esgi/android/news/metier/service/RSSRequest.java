package com.esgi.android.news.metier.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.esgi.android.news.metier.enumeration.EnumNewspaper;
import com.esgi.android.news.metier.model.Item;
import com.esgi.android.news.physique.db.dao.ItemDAO;
import com.esgi.android.news.physique.wb.DownloadData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sam on 01/07/16.
 */
public class RSSRequest extends Service {

    private static final String TAG = "RSSRequest";
    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        List<Item> items = new ArrayList<>();

        DownloadData load = new DownloadData();
        for(EnumNewspaper enumNewspaper : EnumNewspaper.values()){
            load.setFluxRSS(enumNewspaper);
            items = load.downloadNews();

            ItemDAO itemDAO = new ItemDAO(getApplicationContext());
            itemDAO.open();
            for(Item item : items){
                itemDAO.add(item);
            }

            List<Item> itemsRecup = itemDAO.getAll();
            itemDAO.close();
        }


        return Service.START_NOT_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

}
