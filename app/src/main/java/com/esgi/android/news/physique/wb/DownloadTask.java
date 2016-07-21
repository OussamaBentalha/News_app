package com.esgi.android.news.physique.wb;

import android.content.Context;
import android.os.AsyncTask;

import com.esgi.android.news.metier.enumeration.EnumNewspaper;
import com.esgi.android.news.metier.model.Item;
import com.esgi.android.news.physique.db.dao.ItemDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 21/07/16.
 */
public class DownloadTask extends AsyncTask<Void, Integer, Void> {

    private Context context;

    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(context != null){
            List<Item> items = new ArrayList<>();

            DownloadData load = new DownloadData();
            for(EnumNewspaper enumNewspaper : EnumNewspaper.values()){
                load.setFluxRSS(enumNewspaper);
                items = load.downloadNews();

                ItemDAO itemDAO = new ItemDAO(context);
                itemDAO.open();
                for(Item item : items){
                    itemDAO.add(item);
                }

                itemDAO.close();
            }
        }
        return null;
    }
}
