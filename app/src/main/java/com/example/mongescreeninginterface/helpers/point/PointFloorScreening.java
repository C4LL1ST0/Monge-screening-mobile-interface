package com.example.mongescreeninginterface.helpers.point;

import com.example.mongescreeninginterface.projectableObjects.Point3d;

public record PointFloorScreening(float x, float y) {
    public PointFloorScreening(Point3d point3d){
        this(point3d.x, point3d.y);
    }
}
