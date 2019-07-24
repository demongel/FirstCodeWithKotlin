package com.shakespace.firstlinecode.java;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.shakespace.firstlinecode.R;
import com.shakespace.firstlinecode.chapter01activity.FirstActivity;

public class JavaActivity extends AppCompatActivity {

    private static final String TAG = "JavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = this.getClass().getName();
            }
        });


        Intent intent = new Intent(this, FirstActivity.class);
        intent.getClass().getName();
        Intent.class.getName();
        try {
            Class<?> aClass = Class.forName("111");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
