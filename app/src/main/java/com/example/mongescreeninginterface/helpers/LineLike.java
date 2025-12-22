package com.example.mongescreeninginterface.helpers;

import androidx.annotation.Nullable;

import com.example.mongescreeninginterface.projectableObjects.IProjectable;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.helpers.line.LineBothScrs;

public abstract class LineLike<T extends LineLike<T>> extends GeometricObject
        implements IProjectable<T, LineBothScrs> {
    public Point3d firstPoint;
    public Point3d secondPoint;
    @Nullable
    public Point3d floorStopper;
    @Nullable
    public Point3d profileStopper;
    public Vector3d directionVector;

    public LineLike(String name) {
        super(name);
    }
}
