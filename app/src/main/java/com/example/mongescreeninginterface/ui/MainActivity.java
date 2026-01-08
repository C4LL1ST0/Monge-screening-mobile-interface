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
import com.example.mongescreeninginterface.drawable3d.Pyramid;
import com.example.mongescreeninginterface.helpers.IDrawable;
import com.example.mongescreeninginterface.helpers.IRotable;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;

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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        IRotable<?> selectedObject;
        try {
            selectedObject = drawModel.getSelectedObject();
        }catch (RuntimeException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            IRotable<?> rotated = selectedObject.rotate(selectedObject.getPointOfRotation(),
                    5, drawModel.getPlaneOfRotation());
            drawModel.updateObjectToDraw(selectedObject, rotated);
            drawModel.setSelectedObject((IDrawable) rotated);

            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            IRotable<?> rotated = selectedObject.rotate(selectedObject.getPointOfRotation(),
                    355, drawModel.getPlaneOfRotation());
            drawModel.updateObjectToDraw(selectedObject, rotated);
            drawModel.setSelectedObject((IDrawable) rotated);
            return true;
        }else return super.onKeyDown(keyCode, event);
    }
}
