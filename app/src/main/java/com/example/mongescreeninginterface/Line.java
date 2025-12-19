package com.example.mongescreeninginterface;

import androidx.annotation.Nullable;

public class Line extends GeometricObject implements IDrawable<Line> {
    public Point3d startPoint;
    public Point3d endPoint;
    @Nullable
    public Point3d floorStopper;
    @Nullable
    public Point3d profileStopper;

    public Vector3d directionVector;

    public Line(String name, Point3d startPoint, Point3d endPoint){
        super(name);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.directionVector = new Vector3d(startPoint, endPoint);

        var fs = ArithmeticHelperFunctions.findFloorStopper(this);
        if(fs != null) this.floorStopper = fs;

        var ps = ArithmeticHelperFunctions.findProfileStopper(this);
        if(ps != null) this.profileStopper = ps;
    }

    public Line toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        return new Line(name, startPoint.toMachineObject(plotCanvasViewInfo), endPoint.toMachineObject(plotCanvasViewInfo));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Line other = (Line) obj;
        return startPoint.equals(other.startPoint) && endPoint.equals(other.endPoint) ||
                startPoint.equals(other.endPoint) && endPoint.equals(other.startPoint);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(startPoint) + java.util.Objects.hash(endPoint);
    }
}
