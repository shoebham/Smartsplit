package com.example.smartsplit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton aeb;
    static private String phone_number_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
       // phone_number_user=i.getStringExtra("phone_number_user");
        setPhone_number_user(i.getStringExtra("phone_number_user"));
    //    Log.i("numbers",phone_number_user+" in onCreate in MainActivity");
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.myViewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(Color.BLACK);
        tabLayout.setTabTextColors(Color.WHITE, Color.GREEN);

        com.github.clans.fab.FloatingActionButton aeb = findViewById(R.id.aeb);
        aeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_SHORT)
                //        .setAction("Action", null).show();
                openAddExpense();
            }
        });
    }

    //Setting the phone number of the user currently logged in
    public void setPhone_number_user(String phone_number_user) {

        if(phone_number_user==null) {
            Log.i("numbers",this.phone_number_user +" in setPhoneNumber in MainActivity");
        }
        else{
            this.phone_number_user=phone_number_user;
            Log.i("numbers",phone_number_user +" in setPhoneNumber in MainActivity");
        }
    }
    public String getPhone_number_user(String phone_number_user){
        return phone_number_user;
    }
    public void openAddExpense(){
        Intent intent = new Intent(this, AddExpense.class);
        intent.putExtra("phone_number_user", phone_number_user);
        startActivity(intent);
    }

    private void setupViewPager(ViewPager viewPager){

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FriendsFragment(),"Friends");
        viewPagerAdapter.addFragment(new GroupFragment(),"Groups");
        viewPagerAdapter.addFragment(new ActivityFragment(),"Activity");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                //Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(this,"You are logged out",Toast.LENGTH_SHORT).show();
                finish();
                //startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    //
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("phone_number_user",phone_number_user);
//        Log.i("numbers","phone_number_user in onSave"+phone_number_user);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        String phone_number_user2 =  savedInstanceState.getString("phone_number_user");
//        Log.i("numbers","phone_number_user in onRestoreInstanceState"+phone_number_user2);
//    }
}