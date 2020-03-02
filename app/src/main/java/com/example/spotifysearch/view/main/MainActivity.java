package com.example.spotifysearch.view.main;

import android.os.Bundle;

import androidx.annotation.Nullable;


import com.example.spotifysearch.R;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
