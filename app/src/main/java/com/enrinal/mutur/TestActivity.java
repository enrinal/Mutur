package com.enrinal.mutur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void Profile(View view) {
        startActivity(new Intent(TestActivity.this, ProfileViewActivity.class));
        finish();
    }

    public void Ride(View view) {
        startActivity(new Intent(TestActivity.this, MapsActivity.class));
        finish();
    }
}
