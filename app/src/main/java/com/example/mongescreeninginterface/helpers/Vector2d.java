package com.example.mongescreeninginterface.helpers;

import com.example.mongescreeninginterface.projectableObjects.Point3d;

public class Vector2d implements Comparable<Vector2d>{
    public float a;
    public float b;

    public Vector2d(Point3d p1, Point3d p2, PlaneOrientation planeOrientation){
        if(planeOrientation == PlaneOrientation.XY){
            this.a = p2.x - p1.x;
            this.b = p2.y - p1.y;
        }else if(planeOrientation == PlaneOrientation.XZ){
            this.a = p2.x - p1.x;
            this.b = p2.z - p1.z;
        }else {
            this.a = p2.y - p1.y;
            this.b = p2.z - p1.z;
        }
    }
    public double length(){
        return Math.sqrt(a * a + b * b);
    }

    @Override
    public int compareTo(Vector2d other) {
        return Double.compare(this.length(), other.length());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // same reference
        if (obj == null || getClass() != obj.getClass()) return false; // null or different class

        Vector2d other = (Vector2d) obj;

        return Float.compare(a, other.a) == 0 &&
                Float.compare(b, other.b) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(a, b);
    }
}
