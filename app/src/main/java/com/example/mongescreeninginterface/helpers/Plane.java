package com.example.mongescreeninginterface.helpers;

import com.example.mongescreeninginterface.drawableObjects.Point3d;

public class Plane extends GeometricObject {
    public Point3d anchorPoint;
    public Vector3d firstVector;
    public Vector3d secondVector;

    public Plane(String name, Point3d anchorPoint, Vector3d firstVector, Vector3d secondVector){
        super(name);
        this.anchorPoint = anchorPoint;
        this.firstVector = firstVector;
        this.secondVector = secondVector;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // same reference
        if (obj == null || getClass() != obj.getClass()) return false; // null or different class

        Plane other = (Plane) obj;

        return java.util.Objects.equals(anchorPoint, other.anchorPoint)
                && java.util.Objects.equals(firstVector, other.firstVector)
                && java.util.Objects.equals(secondVector, other.secondVector);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(anchorPoint, firstVector, secondVector);
    }

}
