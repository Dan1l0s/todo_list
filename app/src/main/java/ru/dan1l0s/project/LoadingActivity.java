package ru.dan1l0s.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getSupportActionBar().hide();

        Intent intent = new Intent(LoadingActivity.this, DBTestActivity.class); // MainActivity instead of BDTestActivity
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}