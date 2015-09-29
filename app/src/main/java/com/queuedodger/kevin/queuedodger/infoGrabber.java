package com.queuedodger.kevin.queuedodger;

import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kevin on 9/14/2015.
 */
public class infoGrabber implements Runnable {
    private final String USER_AGENT = "Mozilla/5.0";

    public String infoGrabber (String summoner1Name, String summoner2Name, String summoner3Name, String summoner4Name) {

        try

        {
            return getConnection(summoner1Name, summoner2Name, summoner3Name, summoner4Name);
        } catch (MalformedURLException e)

        {
            e.printStackTrace();
        } catch (
                IOException e
                )

        {
            e.printStackTrace();
        } catch (
                Exception e) {
        }

        return "Failed to grab ID";
    }

    @Override
    public void run(){

    }


    private String getConnection(String summoner1NameURL, String summoner2NameURL, String summoner3NameURL, String summoner4NameURL) throws Exception{
        InputStream inputStream = null;
        String str = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/" + summoner1NameURL+ "?api_key=1913e69a-53f2-48a1-9d33-756091d867c6";
        URL url = new URL(str);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) !=null){
            response.append(inputLine);
        }
        JSONObject jsonObj = new JSONObject(response.toString());
        /*JSONArray champions = jsonObj.getJSONArray("id");
        final int n = champions.length();
        for (int i=0; i<n; i++){
            final JSONObject person = champions.getJSONObject(i);
            test.setText(person.getString("id"));
        }
        */
        JSONArray stats = jsonObj.getJSONArray(summoner1NameURL);
        JSONObject player = new JSONObject();
        player = jsonObj.getJSONObject(summoner1NameURL);
        JSONObject playerId = stats.getJSONObject(1);
        return (String.valueOf(playerId.getInt("id")));




        //test.setText(conn.getContent().toString());




    }
}
