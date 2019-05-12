package com.enrinal.mutur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileViewActivity extends AppCompatActivity {
    EditText nama_user, email,noTelp;
    private String userID;
    private String mName;
    private String mPhone;
    private String mEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileview);
        nama_user = findViewById(R.id.nama_user);
        email = findViewById(R.id.email);
        noTelp = findViewById(R.id.noTelp);
        getUserProfile();

    }

    private void getUserProfile() {
        FirebaseUser UID = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(UID.getUid());
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()&&dataSnapshot.getChildrenCount()>0){
                    Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null){
                        String mName = map.get("name").toString();
                        nama_user.setText(mName);
                    }
                    if(map.get("email")!=null){
                        String mEmail = map.get("email").toString();
                        email.setText(mEmail);
                    }
                    if(map.get("uid")!=null){
                        String mPhone = map.get("uid").toString();
                        noTelp.setText(mPhone);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileViewActivity.this, "Error Firebase Request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileViewActivity.this, MapsActivity.class));
        finish();
    }

    public void back(View view) {
        super.onBackPressed();
        startActivity(new Intent(ProfileViewActivity.this, MapsActivity.class));
        finish();
    }

    public void simpan(View view) {
        FirebaseUser UID = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(UID.getUid());
        mName = nama_user.getText().toString();
        mEmail = email.getText().toString();
        Map userInfo = new HashMap();
        userInfo.put("name", mName);
        userInfo.put("email", mEmail);
        mUserDatabase.updateChildren(userInfo);
        Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfileViewActivity.this, MapsActivity.class));
        finish();

    }
}
