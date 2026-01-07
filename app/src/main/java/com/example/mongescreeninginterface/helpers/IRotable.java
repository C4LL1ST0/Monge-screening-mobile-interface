package com.example.mongescreeninginterface.helpers;

import com.example.mongescreeninginterface.projectableObjects.Point3d;

public interface IRotable<T extends  IRotable> {
    T rotate(Point3d pointOfRotation, float angle, PlaneOrientation planeOrientation);
    Point3d getPointOfRotation();
    void setPointOfRotation(Point3d pointOfRotation);
}
