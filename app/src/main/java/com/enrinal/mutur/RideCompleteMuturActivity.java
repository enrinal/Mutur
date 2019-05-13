package com.enrinal.mutur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RideCompleteMuturActivity extends AppCompatActivity {
    private long etaa;
    private TextView eTime;
    private TextView price;
    private TextView mutur_nama;
    private String muturId;
    private long tarif;
    private String hms;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_complete_rating_mutur);
        etaa = getIntent().getLongExtra("eta",Long.MIN_VALUE);
        hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(etaa),
                TimeUnit.MILLISECONDS.toMinutes(etaa) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(etaa)),
                TimeUnit.MILLISECONDS.toSeconds(etaa) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(etaa)));
        price = findViewById(R.id.price);
        if(etaa <= 1800000){
            tarif = 20000;
            price.setText("Rp. 20.000");
        }else {
            tarif = (etaa / 1800000)*20000;
            price.setText( "Rp "+String.valueOf(tarif));

        }
        eTime = findViewById(R.id.eta);
        eTime.setText(hms);
        Log.e("eta", hms);
        muturId = getIntent().getExtras().getString("muturid",null);
        recordMutur();
        mutur_nama=findViewById(R.id.mutur_nama);
        mutur_nama.setText(muturId);
    }

    public void closeView(View view) {
        Intent intent = new Intent(RideCompleteMuturActivity.this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

    private void recordMutur(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("history");
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference().child("history");
        String requestId = historyRef.push().getKey();
        customerRef.child(requestId).setValue(true);
        HashMap map =new HashMap();
        map.put("mutur_id",muturId );
        map.put("user",userId);
        map.put("timestamp", getCurrentTimestamp());
        map.put("price",tarif);
        map.put("waktu_peminjaman",hms);
        historyRef.child(requestId).updateChildren(map);
    }

    private Object getCurrentTimestamp() {
        Long timestamp = System.currentTimeMillis()/1000;
        return timestamp;
    }

    public void back(View view) {
        startActivity(new Intent(RideCompleteMuturActivity.this, MapsActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RideCompleteMuturActivity.this, MapsActivity.class));
        finish();
    }
}
