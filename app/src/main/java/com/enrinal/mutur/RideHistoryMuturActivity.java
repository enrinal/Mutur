package com.enrinal.mutur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import adapter.RidehistoryAdapter;
import model.RidehistoryModel;

public class RideHistoryMuturActivity extends AppCompatActivity {

    private RidehistoryAdapter ridehistoryAdapter;
    private RecyclerView recyclerview;
    private ArrayList<RidehistoryModel> ridehistoryModelArrayList;
    private String userID;
    private String harga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride__history_mutur);
        recyclerview=findViewById(R.id.recycler1);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(RideHistoryMuturActivity.this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ridehistoryAdapter = new RidehistoryAdapter(RideHistoryMuturActivity.this,getDataSetHistory());
        recyclerview.setAdapter(ridehistoryAdapter);
        getUserHistoryIds();
    }

    private void getUserHistoryIds() {
        DatabaseReference userHistoryDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("history");
        userHistoryDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot history : dataSnapshot.getChildren()){
                        FetchRideInformation(history.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void FetchRideInformation(String key) {
        DatabaseReference historyDatabase = FirebaseDatabase.getInstance().getReference().child("history").child(key);
        historyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String rideId = dataSnapshot.getKey();
                    String nilai;
                    Long timestamp = 0L;
                    String muturid = null,waktu = null;
                    int price = 0;
                    for(DataSnapshot child : dataSnapshot.getChildren()){
//                        RidehistoryModel mutur = child.getValue(RidehistoryModel.class);
                        if (child.getKey().equals("timestamp")){
                            timestamp = Long.valueOf(child.getValue().toString());
                        }
                        if (child.getKey().equals("mutur_id")){
                            muturid = child.getValue().toString();
                        }
                        if (child.getKey().equals("price")){
                            price = Integer.valueOf(child.getValue().toString());
                        }
                        if (child.getKey().equals("waktu_peminjaman")){
                            waktu = child.getValue().toString();
                        }
                    }
                    nilai =String.valueOf(price);
                    harga = "Rp. "+nilai;
                    RidehistoryModel obj =new RidehistoryModel(R.drawable.placeholder,
                            R.drawable.rect_dotted,R.drawable.navigatiob_blue,muturid,harga,getDate(timestamp),rideId,waktu);
                    resultsHistory.add(obj);
                    ridehistoryAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private String getDate(Long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time*1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm", cal).toString();
        return date;
    }

    private ArrayList resultsHistory = new ArrayList<RidehistoryModel>();
    private ArrayList<RidehistoryModel> getDataSetHistory() {
        return resultsHistory;
    }

    public void profile_click(View view) {
        startActivity(new Intent(RideHistoryMuturActivity.this, ProfileViewActivity.class));
        finish();
    }

    public void back(View view) {
        startActivity(new Intent(RideHistoryMuturActivity.this, MapsActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RideHistoryMuturActivity.this, MapsActivity.class));
        finish();
    }
}
