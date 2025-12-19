package com.example.mongescreeninginterface.helpers.point;

import com.example.mongescreeninginterface.drawableObjects.Point3d;

public record PointBothScreenings(String name, PointFloorScreening pointFloorScreening, PointProfileScreening pointProfileScreening) {
    public PointBothScreenings(Point3d point3d){
        this(point3d.name, new PointFloorScreening(point3d), new PointProfileScreening(point3d));
    }
}
