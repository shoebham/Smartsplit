//package com.example.smartsplit;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//
//public class HomeActivity extends AppCompatActivity {
//    Button btnLogout;
//    Button btnHome;
//    FirebaseAuth mFirebaseAuth;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        btnLogout = findViewById(R.id.logout);
//        btnHome = findViewById(R.id.tohome);
//
//        btnHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                openMainActivity();
//            }
//        });
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intToMain = new Intent(HomeActivity.this, SignupActivity.class);
//                startActivity(intToMain);
//            }
//        });
//    }
//
//    public void  openMainActivity(){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
//}