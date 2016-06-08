package com.esgi.android.news.metier.utils;

import android.util.Log;
import android.util.Xml;

import com.esgi.android.news.metier.model.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sam on 08/06/16.
 */
public class XmlBodyParser {

    XmlPullParser parser;
    ArrayList<Item> items = null;

    //COMMON TAGS
    private static final String ITEM_TAG = "item";
    private static final String FEED_TAG = "feed";
    private static final String ENTRIES_TAG = "entries";

    private static final String TITLE_TAG = "title";
    private static final String LINK_TAG = "link";
    private static final String DESCRIPTION_TAG = "description";
    private static final String PUBDATE_TAG = "pubDate";
    private static final String ENCLOSURE_TAG = "enclosure";

    public XmlBodyParser(String xmlContent){
        parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xmlContent));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public List<Item> parse(){
        Item currentItem = null;
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String name;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT :
                        items = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG :
                        name = parser.getName();
                        Log.i("NAME", name);
                        if(name.equals(ITEM_TAG)){
                            currentItem = new Item();
                        } else if(currentItem != null){
                            switch (name){
                                case TITLE_TAG:
                                    currentItem.setTitle(parser.nextText());
                                    break;
                                case LINK_TAG: currentItem.setUrlItem(parser.nextText());
                                    break;
                                case DESCRIPTION_TAG: currentItem.setDescription(parser.nextText());
                                    break;
                                case PUBDATE_TAG: SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                                    //String daate = parser.nextText();
                                    //Date date = formatter.parse(daate);
                                    //currentItem.setDate(date);
                                    break;
                                case ENCLOSURE_TAG: String urlImg = parser.getAttributeValue(0);
                                    currentItem.setUrlPicture(urlImg);
                                    break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if(name.equalsIgnoreCase(ITEM_TAG) && currentItem != null){
                            items.add(currentItem);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (ParseException e) {
            e.printStackTrace();
        }*/

        return items;
    }


}
