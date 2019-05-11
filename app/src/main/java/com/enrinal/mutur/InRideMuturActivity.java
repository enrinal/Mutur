package com.enrinal.mutur;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class InRideMuturActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean running;
    private String muturID;
    private TextView nama_mutur;
    private long elapsedMillis ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_in_mutur);
        chronometer = findViewById(R.id.time);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        muturID =getIntent().getExtras().getString("Mutur ID");
        nama_mutur= findViewById(R.id.nama_mutur);
        nama_mutur.setText(muturID);

    }

    @Override
    public void onBackPressed() {

    }

    public void finish_mutur(View view) {
        chronometer.stop();
        elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        Intent intent = new Intent(InRideMuturActivity.this,Ride_complete_rating_icab.class);
        startActivity(intent);
    }
}
