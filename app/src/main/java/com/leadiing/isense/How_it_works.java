package com.leadiing.isense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class How_it_works extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_works);

        //action bar remove
        getSupportActionBar().hide();
    }
}