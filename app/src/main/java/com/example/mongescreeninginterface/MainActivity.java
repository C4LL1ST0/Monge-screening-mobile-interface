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

//        drawModel.addObjectToDraw(new Segment("q", new Point3d("C", 0, 5, 1), new Point3d("D", 0, -1, 1)));
//        drawModel.addObjectToDraw(new Segment("l", new Point3d("A", 1, 2, 3), new Point3d("B", 3, 2, 1)));
//        drawModel.addObjectToDraw(new Segment("c", new Point3d("E", -2, -2, 3), new Point3d("F", -2.5f, -2, -3)));
//        drawModel.addObjectToDraw(new Segment("u", new Point3d("G", -4, 2, 0), new Point3d("H", -4, 2, 3)));
          drawModel.addObjectToDraw(new Segment("j", new Point3d("I", 0, 5, 0.5f), new Point3d("J", 4, 1, 8)));
//        drawModel.addObjectToDraw(new Segment("j", new Point3d("I", 0, 0, 0), new Point3d("J", -4, -4, -4)));
//        drawModel.addObjectToDraw(new Segment("j", new Point3d("I", -2, -2, -2), new Point3d("J", 2, 2, 2)));


        plotCanvas.setDrawModel(drawModel);


        setContentView(plotCanvas);
    }
}