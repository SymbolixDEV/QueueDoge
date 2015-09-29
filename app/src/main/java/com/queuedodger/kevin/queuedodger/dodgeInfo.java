package com.queuedodger.kevin.queuedodger;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent dodgeIntent = getIntent();
        String summoner1Text = dodgeIntent.getExtras().getString("summoner1Name");
        String summoner2Text = dodgeIntent.getExtras().getString("summoner2Name");
        String summoner3Text = dodgeIntent.getExtras().getString("summoner3Name");
        String summoner4Text = dodgeIntent.getExtras().getString("summoner4Name");
        int summoner1Position = dodgeIntent.getIntExtra("summoner1Position", 1);
        int summoner2Position = dodgeIntent.getIntExtra("summoner2Position", 2);
        int summoner3Position = dodgeIntent.getIntExtra("summoner3Position", 3);
        int summoner4Position = dodgeIntent.getIntExtra("summoner4Position", 4);
        int champSelect1 = dodgeIntent.getIntExtra("champSelect1",0);
        int champSelect2 = dodgeIntent.getIntExtra("champSelect2",0);
        int champSelect3 = dodgeIntent.getIntExtra("champSelect3",0);
        int champSelect4 = dodgeIntent.getIntExtra("champSelect4",0);

        AsyncTask summoner1 = new summoner(summoner1Text,champSelect1,summoner1Position).execute();
        AsyncTask summoner2 = new summoner(summoner2Text,champSelect2,summoner2Position).execute();
        AsyncTask summoner3 = new summoner(summoner3Text,champSelect3,summoner3Position).execute();
        AsyncTask summoner4 = new summoner(summoner4Text,champSelect4,summoner4Position).execute();


        TextView textView1 = new TextView(this);
        try {
            textView1.setText(String.valueOf(summoner1.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        TextView textView2 = new TextView(this);
        try {
            textView2.setText(String.valueOf(summoner2.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //statsText = (TextView) findViewById(R.id.testText);
        //statsText.setText(summoner1.getDisplayedText());
        LinearLayout verticalLayout = new LinearLayout(this);

        //fill parent
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.VERTICAL);

        //Ads
        AdView dodgeAd = new AdView(this);
        dodgeAd.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id2));
        dodgeAd.setAdSize(AdSize.SMART_BANNER);


        AdRequest adRequest = new AdRequest.Builder().build();
        dodgeAd.loadAd(adRequest);

        verticalLayout.addView(textView1);
        verticalLayout.addView(textView2);
        verticalLayout.addView(dodgeAd);
        setContentView(verticalLayout, rlp);

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


}
