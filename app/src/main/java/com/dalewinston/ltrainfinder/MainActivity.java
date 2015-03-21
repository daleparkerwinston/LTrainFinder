package com.dalewinston.ltrainfinder;

import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {


    private Spinner spinner;
    private TextView textViewResults;
    private TextView textViewDisclaimer;

    private static String url = "http://datamine.mta.info/mta_esi.php?key=1c90c74dbc055d891ad78889c285cec6&feed_id=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        textViewResults = (TextView) findViewById(R.id.textView_results);
        textViewDisclaimer = (TextView) findViewById(R.id.textView_disclaimer);

    }

    private void setTextViewResults(String text) {
        textViewResults.setText(text);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        //Get Text from gtfs app!
        String stop = "L11N"; // the stop from the spinner

        ArrayList<String> times = new ArrayList<>();

        try {
            GTFSReader reader = new GTFSReader(url, stop);
            times = reader.getMinutesToNextTrains();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //todo: test this todo!!
        //todo: tehn make new to do lists
        String trainTimes = "";

        textViewDisclaimer.setTextColor(Color.RED);
        textViewDisclaimer.setTextSize(14);
        String disclaimer = "*Times are only for Graham Ave heading into Manhattan.";
        textViewDisclaimer.setText(disclaimer);
        textViewResults.setTextColor(Color.DKGRAY);
        textViewResults.setTextSize(18);
        for(int i = 0; i < times.size(); i++) {
            trainTimes += "Arrival Time: " + times.get(i) + "\n";
        }

        setTextViewResults(trainTimes);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
}


