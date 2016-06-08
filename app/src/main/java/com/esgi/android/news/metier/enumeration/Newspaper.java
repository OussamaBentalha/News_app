package com.esgi.android.news.metier.enumeration;

/**
 * Created by Sam on 08/06/16.
 */
public enum Newspaper {

    LEQUIPE,
    EUROSPORT;

    public static String valueOf(Newspaper male) {
        switch (male){
            case LEQUIPE: return "http://www.lequipe.fr/rss/actu_rss_Football.xml";
            case EUROSPORT: return "http://www.eurosport.fr/football/rss.xml";
        }
        return null;
    }
}
