package com.example.mongescreeninginterface;

import static com.example.mongescreeninginterface.ArithmeticHelperFunctions.pi;
import static com.example.mongescreeninginterface.ArithmeticHelperFunctions.ro;

import androidx.annotation.Nullable;

public class Segment extends GeometricObject implements DrawableObject<Segment>{
    public Point3d startPoint;
    public Point3d endPoint;
    @Nullable
    public Point3d floorStopper;
    @Nullable
    public Point3d profileStopper;

    public Vector3d directionVector;

    public Segment(String name, Point3d startPoint, Point3d endPoint){
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.directionVector = new Vector3d(startPoint, endPoint);

        var fs = ArithmeticHelperFunctions.findFloorStopper(this, pi);
        if(fs != null) this.floorStopper = fs;

        var ps = ArithmeticHelperFunctions.findProfileStopper(this, ro);
        if(ps != null) this.profileStopper = ps;
    }

    public Segment toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        return new Segment(name, startPoint.toMachineObject(plotCanvasViewInfo), endPoint.toMachineObject(plotCanvasViewInfo));
    }
}
