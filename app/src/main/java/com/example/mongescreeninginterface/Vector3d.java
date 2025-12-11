package com.example.mongescreeninginterface;

public class Vector3d implements Comparable<Vector3d> {
    public float xt;
    public float yt;
    public float zt;

    public Vector3d(float xt, float yt, float zt){
        this.xt = xt;
        this.yt = yt;
        this.zt = zt;
    }

    public Vector3d(Point3d p1, Point3d p2){
        this.xt = p2.x - p1.x;
        this.yt = p2.y - p1.y;
        this.zt = p2.z - p1.z;
    }
    public double length(){
        return Math.sqrt(xt*xt+yt*yt+zt*zt);
    }

    @Override
    public int compareTo(Vector3d other) {
        return Double.compare(this.length(), other.length());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // same reference
        if (obj == null || getClass() != obj.getClass()) return false; // null or different class

        Vector3d other = (Vector3d) obj;

        return Float.compare(xt, other.xt) == 0 &&
                Float.compare(yt, other.yt) == 0 &&
                Float.compare(zt, other.zt) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(xt, yt, zt);
    }
}
