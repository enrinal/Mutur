package com.enrinal.mutur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    private EditText NoTelepon;
    private TextView PhoneID;
    private Button loginButton;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String VerifikasiID;
    private String No_Telepon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        NoTelepon = findViewById(R.id.phone);
        PhoneID = findViewById(R.id.no_id);
        loginButton = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();

        auth = FirebaseAuth.getInstance();
        /*this triggered the observer when users were signed in, signed out, or when the user's ID
        token changed in situations such as token expiry or password change.*/
        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //Mendeteksi Apakah Ada User Yang Sedang Login (Belum Logout)
                if(user != null){
                    //Jika Ada, User Tidak perlu Login Lagi, dan Langsung Menuju Acivity Yang Dituju
                    startActivity(new Intent(PhoneActivity.this, MapsActivity.class));
                    finish();
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        //Melampirkan Listener pada FirebaseAuth saat Activity Dimulai
        auth.addAuthStateListener(stateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(stateListener != null){
            //Menghapus Listener pada FirebaseAuth saat Activity Dihentikan
            auth.removeAuthStateListener(stateListener);
        }
    }

    private void setupVerificationCallback(){
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // Callback didalam sini akan dipanggil/dieksekusi saat terjadi proses pengiriman kode
                // Dan User Diminta untuk memasukan kode verifikasi

                // Untuk Menyimpan ID verifikasi dan kirim ulang token
                VerifikasiID = verificationId;
                resendToken = token;
//                Intent authinten = new Intent(PhoneActivity.this, AuthActivity.class);
//                authinten.putExtra("verifikasiid",VerifikasiID);
//                startActivity(authinten);
//                finish();
                //Resend.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Mendapatkan Kode Verifikasi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential Credential) {
                // Callback disini akan dipanggil saat Verifikasi Selseai atau Berhasil
                Toast.makeText(getApplicationContext(), "Verifikasi Selesai", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(Credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Callback disini akan dipanggil saat permintaan tidak valid atau terdapat kesalahan
                Toast.makeText(getApplicationContext(), "Verifikasi Gagal, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
            }
        };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Sign In Berhasil
                            startActivity(new Intent(PhoneActivity.this, ProfileActivity.class));
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
    public void loginClick(View view) {
        No_Telepon = PhoneID.getText()+NoTelepon.getText().toString();
        setupVerificationCallback();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                No_Telepon, //NO Telepon Untuk Di Verifikasi
                60, //Durasi Waktu Habis
                TimeUnit.SECONDS, //Unit Timeout
                this, //Activity
                callbacks); // OnVerificationStateChangedCallbacks
        Toast.makeText(getApplicationContext(), "Memverifikasi, Mohon Tunggu", Toast.LENGTH_SHORT).show();
    }
}
