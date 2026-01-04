package com.example.mongescreeninginterface.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.mongescreeninginterface.R;
import com.example.mongescreeninginterface.drawable3d.Cube;
import com.example.mongescreeninginterface.drawable3d.Pyramid;
import com.example.mongescreeninginterface.projectableObjects.Line;
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

        PlotCanvas plotCanvas = findViewById(R.id.plotCanvas);

        Button btnX = findViewById(R.id.btnX);
        btnX.setOnClickListener( v -> drawModel.setPlaneOfRotation(PlaneOrientation.YZ));

        Button btnY = findViewById(R.id.btnY);
        btnY.setOnClickListener( v -> drawModel.setPlaneOfRotation(PlaneOrientation.XZ));

        Button btnZ = findViewById(R.id.btnZ);
        btnZ.setOnClickListener( v -> drawModel.setPlaneOfRotation(PlaneOrientation.XY));

        Pyramid p = new Pyramid("P", 7, new Point3d("", 0, 5, 3), 4, 6);
        drawModel.addObjectToDraw(p);
        drawModel.updateObjectToDraw(p, p.rotate(p.getBaseCenter(), 30, PlaneOrientation.YZ));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            drawModel.updateObjectToDraw(drawModel.getSelectedObject(),
                    drawModel.getSelectedObject().rotate(drawModel.getSelectedObject().getPointOfRotation(),
                    5, drawModel.getPlaneOfRotation()));
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            drawModel.updateObjectToDraw(drawModel.getSelectedObject(),
                    drawModel.getSelectedObject().rotate(drawModel.getSelectedObject().getPointOfRotation(),
                            355, drawModel.getPlaneOfRotation()));
            return true;
        }else return super.onKeyDown(keyCode, event);
    }
}
