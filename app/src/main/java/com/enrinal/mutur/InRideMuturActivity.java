package com.enrinal.mutur;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Chronometer;

public class InRideMuturActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean running;
//    long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_in_mutur);
        chronometer = findViewById(R.id.time);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Override
    public void onBackPressed() {

    }
}
