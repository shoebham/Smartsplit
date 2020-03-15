package com.example.smartsplit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void openAddExpense(){
        Intent intent = new Intent(this, AddExpense.class);
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
}