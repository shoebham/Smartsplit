package com.example.smartsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton aeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tablayout);

        ViewPager viewPager = findViewById(R.id.myViewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton aeb = findViewById(R.id.aeb);
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
}