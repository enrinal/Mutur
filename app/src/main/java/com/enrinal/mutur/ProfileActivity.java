package com.enrinal.mutur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private EditText Nama,Email;
    String NameHolder, EmailHolder;
    DatabaseReference mDatabase;
    //public static final String Database_Path = "Users";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Nama = findViewById(R.id.nama);
        Email = findViewById(R.id.email);
    }

    public void onClick(View view) {
        User user = new User();
        GetDataFromEditText();
        FirebaseUser UID = FirebaseAuth.getInstance().getCurrentUser();
        user.setName(NameHolder);
        user.setEmail(EmailHolder);
        user.setUid(UID.getUid());
        //String UserRecordIDFromServer = mFirebaseDatabaseReference.push().getKey();
        mDatabase.child("users").child(UID.getUid()).setValue(user);
        Toast.makeText(ProfileActivity.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();
        startActivity(new Intent(ProfileActivity.this, MapsActivity.class));
        finish();
    }

    public void GetDataFromEditText(){

        NameHolder = Nama.getText().toString().trim();

        EmailHolder = Email.getText().toString().trim();

    }

    private void saveuser() {
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    }


}

