package com.example.mongescreeninginterface.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.mongescreeninginterface.R;
import com.example.mongescreeninginterface.helpers.IDrawable;
import com.example.mongescreeninginterface.helpers.IManipulatable;
import com.example.mongescreeninginterface.helpers.IMovable;
import com.example.mongescreeninginterface.helpers.IRotable;
import com.example.mongescreeninginterface.helpers.UserObjectAction;
import com.example.mongescreeninginterface.helpers.UserObjectActionInfo;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;

import java.time.Duration;

public class MainActivity extends AppCompatActivity {
    private final DrawModel drawModel = DrawModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnTransfer = findViewById(R.id.btnToControlActivity);
        btnTransfer.setOnClickListener(v -> {
            var controlActivityIntent = new Intent(this, ControlActivity.class);
            startActivity(controlActivityIntent);
        });

        Button btnX = findViewById(R.id.btnX);
        btnX.setOnClickListener( v -> drawModel.setPlaneOfRotation(PlaneOrientation.YZ));

        Button btnY = findViewById(R.id.btnY);
        btnY.setOnClickListener( v -> drawModel.setPlaneOfRotation(PlaneOrientation.XZ));

        Button btnZ = findViewById(R.id.btnZ);
        btnZ.setOnClickListener( v -> drawModel.setPlaneOfRotation(PlaneOrientation.XY));

        Button btnRM = findViewById(R.id.btnRM);
        btnRM.setOnClickListener(v -> drawModel.toggleAction());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != KeyEvent.KEYCODE_VOLUME_DOWN && keyCode != KeyEvent.KEYCODE_VOLUME_UP){
            return super.onKeyDown(keyCode, event);
        }
        try {
            drawModel.doUserAction(keyCode);
        } catch (RuntimeException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
