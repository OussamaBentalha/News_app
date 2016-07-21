package com.esgi.android.news.physique.wb;

import android.os.AsyncTask;

import com.esgi.android.news.metier.enumeration.EnumNewspaper;
import com.esgi.android.news.metier.model.Item;
import com.esgi.android.news.metier.utils.XmlBodyParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 08/06/16.
 */
public class DownloadData {

    private EnumNewspaper fluxRSS;
    private List<Item> items;

    public DownloadData(){
        fluxRSS = EnumNewspaper.EUROSPORT;
        items = new ArrayList<>();
    }

    public List<Item> downloadNews(){

        String response = "";
        MyHttpRequest request = new MyHttpRequest();
        try {
            response = request.download(EnumNewspaper.valueOf(fluxRSS));
            XmlBodyParser parser = new XmlBodyParser(response);
            items = parser.parse();

            for(Item item : items){
                item.setMagazine(fluxRSS.name());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return items;
    }

    public void setFluxRSS(EnumNewspaper fluxRSS) {
        this.fluxRSS = fluxRSS;
    }


}
