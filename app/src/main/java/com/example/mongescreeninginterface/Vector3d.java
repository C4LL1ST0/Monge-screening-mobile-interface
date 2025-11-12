package com.example.mongescreeninginterface;

public class Vector3d {
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
}
