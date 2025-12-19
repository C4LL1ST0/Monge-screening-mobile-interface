package com.example.mongescreeninginterface.helpers.point;

import com.example.mongescreeninginterface.drawableObjects.Point3d;

public record PointProfileScreening(float x, float z) {
    public PointProfileScreening(Point3d point3d){
        this(point3d.x, point3d.z);
    }
}
