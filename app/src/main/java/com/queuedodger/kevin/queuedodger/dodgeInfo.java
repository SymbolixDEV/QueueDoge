package com.queuedodger.kevin.queuedodger;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.queuedodger.kevin.queuedodger.summoners.Statcard;
import com.queuedodger.kevin.queuedodger.summoners.summoner;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class dodgeInfo extends AppCompatActivity {
    private TextView statsText;
    private TextView textView;
    private Statcard stat1,stat2,stat3,stat4;
    private String summoner1Text,summoner2Text,summoner3Text,summoner4Text;
    private String summoner1Champ,summoner2Champ,summoner3Champ,summoner4Champ;
    private TextView summoner1TextView, summoner2TextView, summoner3TextView, summoner4TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent dodgeIntent = getIntent();
        summoner1Text = dodgeIntent.getExtras().getString("summoner1Name");
        summoner2Text = dodgeIntent.getExtras().getString("summoner2Name");
        summoner3Text = dodgeIntent.getExtras().getString("summoner3Name");
        summoner4Text = dodgeIntent.getExtras().getString("summoner4Name");
        summoner1Champ = dodgeIntent.getExtras().getString("summoner1Champ");
        summoner2Champ = dodgeIntent.getExtras().getString("summoner2Champ");
        summoner3Champ = dodgeIntent.getExtras().getString("summoner3Champ");
        summoner4Champ = dodgeIntent.getExtras().getString("summoner4Champ");
        int summoner1Position = dodgeIntent.getIntExtra("summoner1Position", 1);
        int summoner2Position = dodgeIntent.getIntExtra("summoner2Position", 2);
        int summoner3Position = dodgeIntent.getIntExtra("summoner3Position", 3);
        int summoner4Position = dodgeIntent.getIntExtra("summoner4Position", 4);
        int champSelect1 = dodgeIntent.getIntExtra("champSelect1", 0);
        int champSelect2 = dodgeIntent.getIntExtra("champSelect2", 0);
        int champSelect3 = dodgeIntent.getIntExtra("champSelect3", 0);
        int champSelect4 = dodgeIntent.getIntExtra("champSelect4", 0);


        setContentView(R.layout.activity_dodge_info);

        summoner1TextView = (TextView) findViewById(R.id.infosummoner);
        summoner2TextView = (TextView) findViewById(R.id.infosummoner2);
        summoner3TextView = (TextView) findViewById(R.id.infosummoner3);
        summoner4TextView = (TextView) findViewById(R.id.infosummoner4);

        TextView summoner1WinRate = (TextView) findViewById(R.id.positionwinRate);
        TextView summoner2WinRate = (TextView) findViewById(R.id.positionwinRate2);
        TextView summoner3WinRate = (TextView) findViewById(R.id.positionwinRate3);
        TextView summoner4WinRate = (TextView) findViewById(R.id.positionwinRate4);

        /*
        checkAndSet(summoner1TextView, summoner1Text);
        checkAndSet(summoner2TextView, summoner2Text);
        checkAndSet(summoner3TextView, summoner3Text);
        checkAndSet(summoner4TextView, summoner4Text);
        */


        //Set Summoner Name in BOLD
        summoner1TextView.setTypeface(null, Typeface.BOLD);
        summoner2TextView.setTypeface(null, Typeface.BOLD);
        summoner3TextView.setTypeface(null, Typeface.BOLD);
        summoner4TextView.setTypeface(null, Typeface.BOLD);


        summoner1TextView.setText(summoner1Text);
        summoner2TextView.setText(summoner2Text);
        summoner3TextView.setText(summoner3Text);
        summoner4TextView.setText(summoner4Text);

        //set Champion Text
        TextView championText = (TextView) findViewById(R.id.champword);
        TextView championText2 = (TextView) findViewById(R.id.champword2);
        TextView championText3 = (TextView) findViewById(R.id.champword3);
        TextView championText4 = (TextView) findViewById(R.id.champword4);


        championText.setText(summoner1Champ+": ");
        championText2.setText(summoner2Champ+": ");
        championText3.setText(summoner3Champ+": ");
        championText4.setText(summoner4Champ+": ");


        //Set Position Text

        TextView position1Text = (TextView) findViewById(R.id.positionText);
        TextView position2Text = (TextView) findViewById(R.id.positionText2);
        TextView position3Text = (TextView) findViewById(R.id.positionText3);
        TextView position4Text = (TextView) findViewById(R.id.positionText4);

        setPositionText(position1Text,summoner1Position);
        setPositionText(position2Text,summoner2Position);
        setPositionText(position3Text,summoner3Position);
        setPositionText(position4Text,summoner4Position);



        stat1 = new Statcard();
        stat2 = new Statcard();
        stat3 = new Statcard();
        stat4 = new Statcard();

        TextView champkda1 = (TextView) findViewById(R.id.champkda);
        TextView champkda2 = (TextView) findViewById(R.id.champkda2);
        TextView champkda3 = (TextView) findViewById(R.id.champkda3);
        TextView champkda4 = (TextView) findViewById(R.id.champkda4);

        TextView champWinrate1 = (TextView) findViewById(R.id.champWinrate);
        TextView champWinrate2 = (TextView) findViewById(R.id.champWinrate2);
        TextView champWinrate3 = (TextView) findViewById(R.id.champWinrate3);
        TextView champWinrate4 = (TextView) findViewById(R.id.champWinrate4);



        AsyncTask summoner1 = new summoner(summoner1Text.trim(),champSelect1,summoner1Position, champkda1,summoner1WinRate, champWinrate1).execute();
        AsyncTask summoner2 = new summoner(summoner2Text.trim(),champSelect2,summoner2Position, champkda2,summoner2WinRate, champWinrate2).execute();
        AsyncTask summoner3 = new summoner(summoner3Text.trim(),champSelect3,summoner3Position, champkda3,summoner3WinRate, champWinrate3).execute();
        AsyncTask summoner4 = new summoner(summoner4Text.trim(),champSelect4,summoner4Position, champkda4,summoner4WinRate, champWinrate4).execute();



        /*
        //Summoner1
        TextView champkda1 = (TextView) findViewById(R.id.champkda);
        TextView summoner1PositionTextView = (TextView) findViewById(R.id.positionText);
        setPositionText(summoner1PositionTextView, summoner1Position);
        try {
            stat1 = (Statcard) summoner1.get();
            champkda1.setText(String.valueOf(stat1.getChampionWinrate()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Summoner2
        TextView champkda2 = (TextView) findViewById(R.id.champkda2);
        TextView summoner2PositionTextView = (TextView) findViewById(R.id.positionText2);
        setPositionText(summoner2PositionTextView, summoner2Position);
        try {
            stat2 = (Statcard) summoner2.get();
            champkda2.setText(String.valueOf(stat2.getChampionWinrate()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Summoner3
        TextView champkda3 = (TextView) findViewById(R.id.champkda3);
        TextView summoner3PositionTextView = (TextView) findViewById(R.id.positionText3);
        setPositionText(summoner3PositionTextView, summoner3Position);
        try {
            stat3 = (Statcard) summoner3.get();
            champkda3.setText(String.valueOf(stat3.getChampionWinrate()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Summoner4
        TextView champkda4 = (TextView) findViewById(R.id.champkda4);
        TextView summoner4PositionTextView = (TextView) findViewById(R.id.positionText4);
        setPositionText(summoner4PositionTextView, summoner4Position);
        try {
            stat3 = (Statcard) summoner3.get();
            champkda4.setText(String.valueOf(stat4.getChampionWinrate()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */

        //statsText = (TextView) findViewById(R.id.testText);
        //statsText.setText(summoner1.getDisplayedText());

        //Ads
        AdView mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dodge_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private TextView setPositionText(TextView positionText, int positionNumber){
        switch (positionNumber){
            case 0:
                positionText.setText("Middle: ");
                break;
            case 1:
                positionText.setText("Top: ");
                break;
            case 2:
                positionText.setText("Adc: ");
                break;
            case 3:
                positionText.setText("Jungle: ");
                break;
            case 4:
                positionText.setText("Support: ");
                break;
        }


        return positionText;
    }
    private void checkAndSet(TextView summonerTextView, String text){
        if (text == null){
            summonerTextView.setText("");
        }
        else{
            summoner1TextView.setText(text);
        }
    }

}
