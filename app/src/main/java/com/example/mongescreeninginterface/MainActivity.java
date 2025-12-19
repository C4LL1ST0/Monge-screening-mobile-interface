package com.example.mongescreeninginterface;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
        var drawModel = new DrawModel();

//        drawModel.addObjectToDraw(new Segment("q", new Point3d("C", 3, 2, 1), new Point3d("D", -3, 2, 1)));
//        drawModel.addObjectToDraw(new Segment("l", new Point3d("A", 1, 2, 3), new Point3d("B", 3, 2, 1)));
//        drawModel.squashPoints();
        Cube c = new Cube("K", new Point3d("S", 0, 7, 7), 2);
        drawModel.addObjectToDraw(c);
        drawModel.squashPoints();

        plotCanvas.setDrawModel(drawModel);

        setContentView(plotCanvas);
    }
}
