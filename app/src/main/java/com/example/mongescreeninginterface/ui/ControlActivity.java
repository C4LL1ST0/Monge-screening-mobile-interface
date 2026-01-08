package com.example.mongescreeninginterface.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;


import com.example.mongescreeninginterface.R;

import java.time.Duration;


public class ControlActivity extends AppCompatActivity {

    private Button ptIns, lineIns, cubeIns, pyramidIns;
    private EditText ptNameText, ptXText, ptYText, ptZText, lnNameText, lnPt1Text, lnPt2Text,
            cubeNameText, cubeCenterText, cubeEdgeLengthText, pyramidNameText,
            pyramidBaseCenterText, pyramidPtCountText, pyramidRText, pyramidHText;
    private SwitchCompat segmentSwitch;
    private DrawModel drawModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_control);

        Button btnToCanvas = findViewById(R.id.btnToCanvas);
        btnToCanvas.setOnClickListener(v -> finish());

        drawModel = DrawModel.getInstance();

        ptNameText = findViewById(R.id.ptNameText);
        ptXText = findViewById(R.id.ptXText);
        ptYText = findViewById(R.id.ptYText);
        ptZText = findViewById(R.id.ptZText);

        lnNameText = findViewById(R.id.lnNameText);
        lnPt1Text = findViewById(R.id.lnFirstText);
        lnPt2Text = findViewById(R.id.lnSecondtText);
        segmentSwitch = findViewById(R.id.segmentSwitch);

        cubeNameText = findViewById(R.id.cubeNameText);
        cubeCenterText = findViewById(R.id.cubeFirstText);
        cubeEdgeLengthText = findViewById(R.id.cubeEdgeLengthText);

        pyramidNameText = findViewById(R.id.pyramidNameText);
        pyramidBaseCenterText = findViewById(R.id.pyramidBaseCenterText);
        pyramidPtCountText = findViewById(R.id.pyramidPtCountText);
        pyramidRText = findViewById(R.id.pyramidRadiusText);
        pyramidHText = findViewById(R.id.pyramidHeightText);
    }

    public void onPtInsertClick(View view) {
        float x = 0, y = 0, z = 0;
        try{
            x = Integer.parseInt(ptXText.getText().toString());
            y = Integer.parseInt(ptYText.getText().toString());
            z = Integer.parseInt(ptZText.getText().toString());
        }catch (NumberFormatException e){
           Toast.makeText(this, "Not a valid number", Toast.LENGTH_SHORT).show();
           return;
        }
        var name = ptNameText.getText().toString();
        if(name.length() > 1) name = String.valueOf(name.charAt(0));
        try {
            drawModel.addPoint(name.toUpperCase(), x,y,z);
        }catch (RuntimeException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onLineInsertClick(View view) {
        var name = lnNameText.getText().toString();
        if(name.length() > 1) name = String.valueOf(name.charAt(0));

        try{
            drawModel.addLineLike(name, segmentSwitch.isChecked(), lnPt1Text.getText().toString()
                    , lnPt2Text.getText().toString());
        }catch (RuntimeException e){
            Toast.makeText(ControlActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCubeInsertClick(View view) {
        float a;
        try {
            a = Float.parseFloat(cubeEdgeLengthText.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this, "Not a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        var name = cubeNameText.getText().toString();
        if(name.length() > 1) name = String.valueOf(name.charAt(0));
        try {
            drawModel.addCube(name.toUpperCase(), cubeCenterText.getText().toString(), a);
        }catch (RuntimeException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onPyramidInsertClick(View view) {
        float r = 0, h = 0;
        int pointCount = 0;
        try{
            r = Float.parseFloat(pyramidRText.getText().toString());
            h = Float.parseFloat(pyramidHText.getText().toString());
            pointCount = Integer.parseInt(pyramidPtCountText.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this, "Not a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        var name = pyramidNameText.getText().toString();
        if(name.length() > 1) name = String.valueOf(name.charAt(0));
        try {
            drawModel.addPyramid(name, pyramidBaseCenterText.getText().toString(),
                    pointCount, r, h);
        } catch (RuntimeException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}