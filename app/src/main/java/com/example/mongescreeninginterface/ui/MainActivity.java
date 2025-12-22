package com.example.mongescreeninginterface.ui;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.mongescreeninginterface.drawable3d.Cube;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;

public class MainActivity extends AppCompatActivity {

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
        var drawModel = DrawModel.getInstance();

//        drawModel.addObjectToDraw(new Line("q", new Point3d("C", 3, 2, 1), new Point3d("D", -3, 2, 1)));
//        drawModel.addObjectToDraw(new Line("l", new Point3d("A", 1, 2, 3), new Point3d("B", 3, 2, 1)));
//        drawModel.squashPoints();

        Cube c = new Cube("K", new Point3d("S", 0, 5, 5), 4);
        c = c.rotate(c.getCenter(), 30, PlaneOrientation.XY);
        c = c.rotate(c.getCenter(), 20, PlaneOrientation.XZ);
        c = c.rotate(c.getCenter(), 20, PlaneOrientation.YZ);
        drawModel.addObjectToDraw(c);
        drawModel.squashPoints();

        plotCanvas.setDrawModel(drawModel);

        setContentView(plotCanvas);
    }
}
