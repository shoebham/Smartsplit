package com.example.smartsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class logo_page extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_page);

        timer=new Timer();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        if (mFirebaseUser != null) {
                            Log.i("USER EMAIL",mFirebaseUser.getEmail());
                            Intent i = new Intent(logo_page.this, HomeActivity.class);
                            startActivity(i);
                        } else {
                            Intent mainPage = new Intent(logo_page.this, MainActivityo.class);
                            startActivity(mainPage);
                        }
                        finish();
                    }
                },500);
            }
        };

    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
