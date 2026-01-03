package com.example.mongescreeninginterface.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

        PlotCanvas plotCanvas = new PlotCanvas(this);
        plotCanvas.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));


//        drawModel.addObjectToDraw(new Line("q", new Point3d("C", 3, 2, 1), new Point3d("D", -3, 2, 1)));
//        drawModel.addObjectToDraw(new Line("l", new Point3d("A", 1, 2, 3), new Point3d("B", 3, 2, 1)));
//        drawModel.squashPoints();

//        Cube c = new Cube("K", new Point3d("S", 0, 8, 6), 3);
//        drawModel.addObjectToDraw(c);


        Pyramid p = new Pyramid("P", 7, new Point3d("", 0, 5, 3), 4, 6);
        drawModel.addObjectToDraw(p);
        drawModel.updateObjectToDraw(p, p.rotate(p.getBaseCenter(), 30, PlaneOrientation.YZ));

        plotCanvas.setDrawModel();

        setContentView(plotCanvas);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            drawModel.updateObjectToDraw(drawModel.getSelectedObject(),
                    drawModel.getSelectedObject().rotate(drawModel.getSelectedObject().getPointOfRotation(),
                    5, PlaneOrientation.XY));
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            drawModel.updateObjectToDraw(drawModel.getSelectedObject(),
                    drawModel.getSelectedObject().rotate(drawModel.getSelectedObject().getPointOfRotation(),
                            355, PlaneOrientation.XY));
            return true;
        }else return super.onKeyDown(keyCode, event);
    }
}
