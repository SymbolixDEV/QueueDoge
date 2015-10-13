package com.queuedodger.kevin.queuedodger.summoners;

/**
 * Created by Kevin on 9/18/2015.
 */
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.queuedodger.kevin.queuedodger.data.hashmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class summoner extends AsyncTask<String, Integer, Statcard> {
    private JsonElement[] summoner1Champions;
    private static Map<Integer, String> hmap;
    private float effectiveness;
    private String displayedText;
    private String summonerName;
    private int championId, pickPosition;
    private float weightModifier1, weightModifier2;
    private int position, selectedChampion;
    private boolean noPositionGames;
    private Statcard statcard;
    private TextView champkda, positionWinrate,champWinrate;

    private static final String USER_AGENT = "Mozilla/5.0";
    public summoner (String summonerName, int championId, int pickPosition, TextView champkda, TextView positionWinrate, TextView champWinrate){
        weightModifier2 = (float) .7;
        weightModifier1= (float) .3;
        effectiveness = 7;
        this.summonerName = summonerName;
        this.championId = championId;
        this.pickPosition = pickPosition;
        noPositionGames = false;
        statcard = new Statcard();
        this.champkda = champkda;
        this.positionWinrate = positionWinrate;
        this.champWinrate = champWinrate;

        doInBackground();
    }




    private static int[] midChampions = {103, 84, 34,1,268,63,69,131,245,105,74,30,38,55,7,127,99,117,90,61,13,50,134,91,4,45,161,8,101,238,115,76,26,143,43,25};
    private static int[] adcChampions = {22,51,42,119,81,104,222,96,236,21,133,15,18,29,110,67};
    private static int[] topChampions = {266, 31, 122, 36,114,41, 86,150,79,120,39,59,24,126,10,121,85,64,54,57,75,111,56,20,2,80,78,133,421,58,33,107,92,68,13,113,35,98,102,27,14,72,14,50,91,48,23,77,6,254,8,106,62,5,157,83,154};
    private static int[] supportChampions = {12,1,432,53,63,201,9,40,43,10,89,117,25,267,111,76,37,16,44,412,26,143};
    private static int[] jungleChampions = {266,32,31,36,245,60,28,9,105,150,79,120,59,24,121,64,57,54,76,56,20,2,80,33,421,58,107,113,35,98,102,14,72,48,23,77,254,106,19,62,5,154};

    private void getConnection(String summoner1NameURL, int selectedChampion, int position) throws Exception{
        JsonElement summoner1Id;
        hashmap hashmap = new hashmap();
        hmap = hashmap.getHashmap();
        int pickPosition = position;
        this.selectedChampion = selectedChampion;
        statcard.setPosition(position);

        int summoner1IdInt;

        InputStream inputStream = null;
        String str = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/" + summoner1NameURL+ "?api_key=1913e69a-53f2-48a1-9d33-756091d867c6";
        URL url = new URL(str);
        HttpURLConnection idGrabConn = (HttpURLConnection) url.openConnection();
        idGrabConn.connect();
        //conn.setRequestMethod("GET");
        //conn.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = idGrabConn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader((idGrabConn.getInputStream())));
        //String inputLine;
        //StringBuffer response = new StringBuffer();


        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) idGrabConn.getContent()));
        JsonObject obj = root.getAsJsonObject();
        //JsonObject rootObj = root.getAsJsonObject();
        //System.out.println(obj);
        JsonObject user = (JsonObject) obj.get(summoner1NameURL);
        //System.out.println(user);
        summoner1Id = user.get("id");
        summoner1IdInt = summoner1Id.getAsInt();

        //idGrabConn.disconnect();

        String historyURLString = "https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/" + summoner1IdInt + "/ranked?season=" + "SEASON2015" + "&api_key=1913e69a-53f2-48a1-9d33-756091d867c6";
        URL historyURL = new URL (historyURLString);
        HttpURLConnection rankedGrabConn = (HttpURLConnection) historyURL.openConnection();
        rankedGrabConn.connect();


        int rankedResponseCode = rankedGrabConn.getResponseCode();
        if (rankedResponseCode == 400){
            displayedText = "Bad Request";
        }
        if (rankedResponseCode == 401){
            displayedText = "Unauthorized";
        }
        if (rankedResponseCode == 503){
            displayedText = "Server Unavailable";
        }

        else {

            BufferedReader rankedReader = new BufferedReader(new InputStreamReader((rankedGrabConn.getInputStream())));
            JsonElement rankedRoot = jp.parse(new InputStreamReader((InputStream) rankedGrabConn.getContent()));
            JsonObject rankedObj = rankedRoot.getAsJsonObject();
            JsonArray championList = rankedObj.getAsJsonArray("champions");
            System.out.println(championList);
            int totalTopWins = 0, totalTopGames = 0, totalMidWins = 0, totalMidGames = 0, totalJungleWins = 0, totalJungleGames = 0, totalAdcWins = 0, totalAdcGames = 0, totalSupportWins = 0, totalSupportGames = 0;
            int selectedChampGames = 0, selectedChampWins = 0, selectedChampKills = 0, selectedChampDeaths = 0, selectedChampAssists = 0;
            JsonObject statsObj = null;
            for (int i = 0; i < championList.size(); i++) {
                System.out.println(championList.get(i));
                JsonObject tempObj = (JsonObject) championList.get(i);
                //tempObj.get("stats");
                JsonPrimitive idObj = tempObj.getAsJsonPrimitive("id");
                statsObj = (JsonObject) tempObj.get("stats");
                JsonElement totalGamesElement = statsObj.get("totalSessionsPlayed");
                JsonElement totalWinsElement = statsObj.get("totalSessionsWon");
                JsonElement totalKillsElement = statsObj.get("totalChampionKills");
                JsonElement totalDeathsElement = statsObj.get("totalDeathsPerSession");
                JsonElement totalAssistsElement = statsObj.get("totalAssists");

                if (totalWinsElement.getAsInt() > 4) {
                    System.out.println((float) totalWinsElement.getAsInt() / totalGamesElement.getAsInt());
                } else {
                    System.out.println("Not enough data!");
                }
                System.out.println("KDA is:" + (float) (totalKillsElement.getAsInt() + totalAssistsElement.getAsInt()) / totalDeathsElement.getAsInt());

                if (idObj.getAsInt() == selectedChampion) {
                    selectedChampGames = totalGamesElement.getAsInt();
                    selectedChampWins = totalWinsElement.getAsInt();
                    selectedChampKills = totalKillsElement.getAsInt();
                    selectedChampDeaths = totalDeathsElement.getAsInt();
                    selectedChampAssists = totalAssistsElement.getAsInt();
                    statcard.setKda((selectedChampKills+selectedChampAssists)/selectedChampDeaths);
                }

                switch (pickPosition) {
                    case 0:
                        for (int j = 0; j < midChampions.length; j++) {
                            if (idObj.getAsInt() == midChampions[j]) {
                                totalMidGames += totalGamesElement.getAsInt();
                                totalMidWins += totalWinsElement.getAsInt();
                            }
                        }
                        break;
                    case 1:
                        for (int j = 0; j < topChampions.length; j++) {
                            if (idObj.getAsInt() == topChampions[j]) {
                                totalTopGames += totalGamesElement.getAsInt();
                                totalTopWins += totalWinsElement.getAsInt();
                            }
                        }
                        break;


                    case 2:
                        for (int j = 0; j < adcChampions.length; j++) {
                            if (idObj.getAsInt() == adcChampions[j]) {
                                totalAdcGames += totalGamesElement.getAsInt();
                                totalAdcWins += totalWinsElement.getAsInt();
                            }
                        }
                        break;

                    case 3:
                        for (int j = 0; j < jungleChampions.length; j++) {
                            if (idObj.getAsInt() == jungleChampions[j]) {
                                totalJungleGames += totalGamesElement.getAsInt();
                                totalJungleWins += totalWinsElement.getAsInt();
                            }
                        }
                        break;

                    case 4:
                        for (int j = 0; j < supportChampions.length; j++) {
                            if (idObj.getAsInt() == supportChampions[j]) {
                                totalSupportGames += totalGamesElement.getAsInt();
                                totalSupportWins += totalWinsElement.getAsInt();
                            }
                        }
                        break;
                }


            }


            //System.out.println(rankedObj);
            float selectedChampWinRate = (float) selectedChampWins / selectedChampGames;
            statcard.setChampionWinrate(selectedChampWinRate);

            float positionWinRate = 0;

            switch (position) {
                case 0:
                    System.out.println("Mid Winrate:" + (float) totalMidWins / (float) totalMidGames);
                    positionWinRate = (float) totalMidWins / (float) totalMidGames;
                    statcard.setPositionWinrate(positionWinRate);
                    //check for games
                    if (totalMidGames == 0){
                        noPositionGames = true;
                    }
                    break;

                case 1:
                    System.out.println("Top Winrate:" + (float) totalTopWins / totalTopGames);
                    positionWinRate = (float) totalTopWins / (float) totalTopGames;
                    statcard.setPositionWinrate(positionWinRate);
                    if (totalTopGames == 0){
                        noPositionGames = true;
                    }
                    break;

                case 2:
                    System.out.println("Adc Winrate:" + (float) totalAdcWins / totalAdcGames);
                    positionWinRate = (float) totalAdcWins / (float) totalAdcGames;
                    statcard.setPositionWinrate(positionWinRate);
                    if (totalAdcGames == 0){
                        noPositionGames = true;
                    }
                    break;

                case 3:
                    System.out.println("Jungle Winrate:" + (float) totalJungleWins / totalJungleGames);
                    positionWinRate = (float) totalJungleWins / (float) totalJungleGames;
                    statcard.setPositionWinrate(positionWinRate);
                    if (totalJungleGames == 0){
                        noPositionGames = true;
                    }
                    break;
                case 4:
                    System.out.println("Support Winrate:" + (float) totalSupportWins / totalSupportGames);
                    positionWinRate = (float) totalSupportWins / (float) totalSupportGames;
                    statcard.setPositionWinrate(positionWinRate);
                    if (totalSupportGames == 0){
                        noPositionGames = true;
                    }
                    break;

            }

            //System.out.println("Effectiveness of:" + hmap.get(selectedChampion));
            //System.out.println(selectedChampWinRate*.7 + positionWinRate *.3);
            float weightedSelectedChampWinRate = selectedChampWinRate * weightModifier2;
            float weightedPositionWinRate = positionWinRate * weightModifier1;
            if (selectedChampGames == 0 && noPositionGames == true){
                displayedText = "No games for position & champion";
            }
            if (selectedChampGames>0 && noPositionGames == false) {
                effectiveness = weightedPositionWinRate + weightedSelectedChampWinRate;
                displayedText = String.valueOf(getEffectiveness());
            }

            if (selectedChampGames>0 && noPositionGames){
                displayedText = String.valueOf(selectedChampWinRate);
            }

            else {
                displayedText= String.valueOf(positionWinRate);
            }
            //displayedText = String.valueOf(summoner1IdInt);
        }

        rankedGrabConn.disconnect();
    }

    public float getEffectiveness() {
        return effectiveness;
    }

    public String getDisplayedText() {return displayedText;}

    public void setDisplayedText(String display) {displayedText = display;}

    @Override
    protected Statcard doInBackground(String... strings) {
        try {
            getConnection(summonerName, championId, pickPosition);
        }
        catch (IOException ie){
            setDisplayedText("IO Exception!");
        }

        catch (Exception e) {
            // TODO Auto-generated catch block
            setDisplayedText("General Exception!");
            e.printStackTrace();
        }
        return statcard;
    }

    protected void onPostExecute(Statcard result){

        champkda.setText(String.format("%.2f",statcard.getkda()));
        if (statcard.getkda()>=3){
            champkda.setTextColor(Color.GREEN);
        }
        if (statcard.getkda()<3 && statcard.getkda()>= 1.5){
            champkda.setTextColor(Color.YELLOW);
        }
        if (statcard.getkda()<1.5){
            champkda.setTextColor(Color.RED);
        }
        positionWinrate.setText(String.format("%.2f",statcard.getPositionWinrate()));
        champWinrate.setText(String.format("%.2f",statcard.getChampionWinrate()));
        winRateColorSet(positionWinrate, statcard.getPositionWinrate());
        winRateColorSet(champWinrate, statcard.getChampionWinrate());
    }

    private void winRateColorSet(TextView View, float number){
        if (number > 0.55){
            View.setTextColor(Color.GREEN);
        }
        if (number <= 0.55 & number > 0.48){
            View.setTextColor(Color.YELLOW);
        }
        if (number <= 0.48){
            View.setTextColor(Color.RED);
        }
    }
}
