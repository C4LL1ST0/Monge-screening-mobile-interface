package com.example.mongescreeninginterface;

public class Plane {
    public Point3d anchorPoint;
    public Vector3d firstVector;
    public Vector3d secondVector;

    public Plane(Point3d anchorPoint, Vector3d firstVector, Vector3d secondVector){
        this.anchorPoint = anchorPoint;
        this.firstVector = firstVector;
        this.secondVector = secondVector;
    }
}
