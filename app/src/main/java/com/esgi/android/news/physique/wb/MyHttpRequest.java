package com.esgi.android.news.physique.wb;

import android.os.AsyncTask;
import java.net.*;
import java.io.*;


/**
 * Created by Sam on 04/05/16.
 */
public class MyHttpRequest extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... params) {
        String inputLine = "";
        String response = "";
        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((inputLine = in.readLine()) != null){
                response += inputLine;
            }

            in.close();
        } catch (MalformedURLException ex){

        } catch (IOException ex){

        }

        return  response;
    }

}
