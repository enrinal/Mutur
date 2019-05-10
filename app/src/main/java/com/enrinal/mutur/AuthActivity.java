package com.enrinal.mutur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String VerifikasiID = getIntent().getStringExtra("verifikasiid");
    private String VerifikasiCode;
    private String No_Telepon;
    EditText verivikasicode = (EditText)findViewById(R.id.verifikasi_id);




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        String VerifikasiCode =verivikasicode.getText().toString();
        if(TextUtils.isEmpty(VerifikasiCode)){
            //Memberi Pesan pada user bahwa kode verifikasi tidak boleh kosong saat menekan Tombol Verifikasi
            Toast.makeText(getApplicationContext(),"Masukan Kode Verifikasi", Toast.LENGTH_SHORT).show();
        }else{
            //Memverifikasi Nomor Telepon, Saat Tombol Verifikasi Ditekan
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerifikasiID, VerifikasiCode);
            signInWithPhoneAuthCredential(credential);
            Toast.makeText(getApplicationContext(),"Sedang Memverifikasi", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Sign In Berhasil
                            startActivity(new Intent(AuthActivity.this, ProfileActivity.class));
                            finish();
                        }else{
                            //Sign In Gagal
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                // Kode Yang Dimasukan tidal Valid.
                                Toast.makeText(getApplicationContext(), "Kode yang dimasukkan tidak valid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
