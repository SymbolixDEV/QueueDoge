package com.queuedodger.kevin.queuedodger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.queuedodger.kevin.queuedodger.data.hashmap;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String,Integer> inv, invertedHash;
    private hashmap championHash;
    private Spinner champSpinner1,champSpinner2,champSpinner3,champSpinner4;

    public final static String summoner1Name = "com.queuedodger.kevin.summoner1Name";
    public final static String summoner2Name = "com.queuedodger.kevin.summoner2Name";
    public final static String summoner3Name = "com.queuedodger.kevin.summoner3Name";
    public final static String summoner4Name = "com.queuedodger.kevin.summoner4Name";
    //1913e69a-53f2-48a1-9d33-756091d867c6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //curl --request GET 'https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/RiotSchmick?api_key=<key>' --include

        hashmap championHashHolder = new hashmap();
        Map<Integer,String> champHash = championHashHolder.getHashmap();
        //Map<String,Integer> invertedHash = new HashMap<>();
        invertedHash = inv(champHash);
        champSpinner1 = (Spinner) findViewById(R.id.champSpinner1);
        champSpinner2 = (Spinner) findViewById(R.id.champSpinner2);
        champSpinner3 = (Spinner) findViewById(R.id.champSpinner3);
        champSpinner4 = (Spinner) findViewById(R.id.champSpinner4);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClickDodge(View view){
        Intent dodgeIntent = new Intent(this, dodgeInfo.class);
        EditText summoner1 = (EditText) findViewById(R.id.summoner1);
        String summoner1Text = summoner1.getText().toString();
        EditText summoner2 = (EditText) findViewById(R.id.summoner2);
        String summoner2Text = summoner2.getText().toString();
        EditText summoner3 = (EditText) findViewById(R.id.summoner3);
        String summoner3Text = summoner3.getText().toString();
        EditText summoner4 = (EditText) findViewById(R.id.summoner4);
        String summoner4Text = summoner4.getText().toString();
        dodgeIntent.putExtra("summoner1Name", summoner1Text);
        dodgeIntent.putExtra("summoner2Name", summoner2Text);
        dodgeIntent.putExtra("summoner3Name", summoner3Text);
        dodgeIntent.putExtra("summoner4Name", summoner4Text);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        dodgeIntent.putExtra("summoner1Position", spinner1.getSelectedItemPosition());
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        dodgeIntent.putExtra("summoner2Position", spinner2.getSelectedItemPosition());
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        dodgeIntent.putExtra("summoner3Position", spinner3.getSelectedItemPosition());
        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        dodgeIntent.putExtra("summoner4Position", spinner4.getSelectedItemPosition());

        dodgeIntent.putExtra("summoner1Champ", champSpinner1.getSelectedItem().toString());
        dodgeIntent.putExtra("summoner2Champ", champSpinner1.getSelectedItem().toString());
        dodgeIntent.putExtra("summoner3Champ", champSpinner1.getSelectedItem().toString());
        dodgeIntent.putExtra("summoner4Champ", champSpinner1.getSelectedItem().toString());


        dodgeIntent.putExtra("champSelect1",champConversion(champSpinner1));
        dodgeIntent.putExtra("champSelect2",champConversion(champSpinner2));
        dodgeIntent.putExtra("champSelect3",champConversion(champSpinner3));
        dodgeIntent.putExtra("champSelect4",champConversion(champSpinner4));

        startActivity(dodgeIntent);
    }

    private int champConversion(Spinner champSpinner){
       return invertedHash.get(champSpinner.getSelectedItem().toString());

    }

    private static <String, Integer> Map<String, Integer> inv(Map<Integer, String> map) {

        Map<String,Integer> inv = new HashMap<>();

        for (Map.Entry<Integer, String> entry : map.entrySet())
            inv.put(entry.getValue(), entry.getKey());

        return inv;
    }

}

