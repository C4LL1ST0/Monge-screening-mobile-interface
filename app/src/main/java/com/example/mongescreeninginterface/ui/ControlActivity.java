package com.example.mongescreeninginterface.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.example.mongescreeninginterface.R;



public class ControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_control);

        Button btnToCanvas = findViewById(R.id.btnToCanvas);
        btnToCanvas.setOnClickListener(v -> finish());
    }

    public void onPtInsertClick(View view) {
    }

    public void onLineInsertClick(View view) {
    }

    public void onCubeInsertClick(View view) {
    }

    public void onPyramidInsertClick(View view) {
    }
}