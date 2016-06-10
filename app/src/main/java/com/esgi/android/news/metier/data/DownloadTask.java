package com.esgi.android.news.metier.data;

import android.util.Log;

import com.esgi.android.news.metier.enumeration.Newspaper;
import com.esgi.android.news.metier.model.Item;
import com.esgi.android.news.metier.utils.XmlBodyParser;
import com.esgi.android.news.physique.wb.MyHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 08/06/16.
 */
public class DownloadTask {

    private Newspaper fluxRSS;
    private List<Item> items;

    public DownloadTask(){
        fluxRSS = Newspaper.EUROSPORT;
        items = new ArrayList<>();
    }


    public List<Item> downloadNews(){

        String response = "";
        MyHttpRequest request = new MyHttpRequest();
        try {
            response = request.execute(Newspaper.valueOf(fluxRSS)).get();
            XmlBodyParser parser = new XmlBodyParser(response);
            items = parser.parse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public void setFluxRSS(Newspaper fluxRSS) {
        this.fluxRSS = fluxRSS;
    }


}
