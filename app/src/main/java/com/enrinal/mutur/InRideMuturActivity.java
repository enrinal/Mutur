package com.enrinal.mutur;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class InRideMuturActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean running;
    private String muturID;
    private TextView nama_mutur;
    private long timerTime = Long.MIN_VALUE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_in_mutur);
        chronometer = findViewById(R.id.time);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        timerTime = Long.MIN_VALUE;
        muturID =getIntent().getExtras().getString("Mutur ID");
        nama_mutur= findViewById(R.id.nama_mutur);
        nama_mutur.setText(muturID);
    }

    @Override
    public void onBackPressed() {

    }

    public void finish_mutur(View view) {
        chronometer.stop();
        timerTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        Intent intent = new Intent(InRideMuturActivity.this, RideCompleteMuturActivity.class);
        intent.putExtra("eta",timerTime);
        intent.putExtra("muturid",muturID);
        startActivity(intent);
        Log.e("Time Wat :", String.valueOf(timerTime));
    }
}
