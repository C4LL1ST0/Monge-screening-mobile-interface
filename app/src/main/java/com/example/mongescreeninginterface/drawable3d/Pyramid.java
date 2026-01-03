package com.example.mongescreeninginterface.drawable3d;

import com.example.mongescreeninginterface.helpers.PlaneOrientation;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.projectableObjects.Segment;

public class Pyramid extends Object3d{

    private Point3d baseCenter;
    private float r;
    private float height;

    public Point3d getBaseCenter(){return  baseCenter;}
    public float getR() { return r;}
    public float getHeight() { return height;}

    public Pyramid(String name, Segment[] edges) {
        super(name, edges);
    }

    public Pyramid(String name, int pointCount, Point3d baseCenter, float r, float height){
        super(name);
        this.baseCenter = baseCenter;
        this.r = r;
        this.height = height;

        initiatePoints(pointCount);
        initiateEdges();
    }

    public Pyramid(String name,  Point3d[] points, Point3d baseCenter, float r, float height){
        super(name);
        this.baseCenter = baseCenter;
        this.r = r;
        this.height = height;
        this.points = points;
        initiateEdges();
    }

    private void initiatePoints(int pointCount){
        var rotationAngle = 360f/pointCount;
        points = new Point3d[pointCount + 1];
        points[0] = new Point3d("A", baseCenter.x + r, baseCenter.y, baseCenter.z);
        for(int i = 1; i <= pointCount; i++){
            points[i] = points[i-1].rotate(baseCenter, rotationAngle, PlaneOrientation.XY);
            points[i].name = String.valueOf((char)(65+(i%90)));
        }
        points[points.length-1] = new Point3d(String.valueOf((char)(65+((pointCount+1)%90))),
                baseCenter.x, baseCenter.y, baseCenter.z + height);
    }

    private void initiateEdges(){
        int pointCount = points.length;
        edges = new Segment[pointCount*2];

        int edgeCount = 0;
        for(int i = 0; i < points.length-1; i++){
            edges[i] = new Segment("", points[i], points[i+1]);
            edgeCount = i;
        }

        edges[edgeCount + 1] = new Segment("", points[0], points[points.length-2]);
        edgeCount+=2;

        for(int i = 0; i < points.length-2; i++){
            edges[edgeCount] = new Segment("", points[points.length-1], points[i]);
            if(edgeCount < edges.length-1)
                ++edgeCount;
        }
    }

    public Pyramid rotate(Point3d pointOfRotation, float angle, PlaneOrientation planeOrientation){
        var rotatedPoints = new Point3d[points.length];
        for(int i = 0; i < rotatedPoints.length; i++){
            rotatedPoints[i] = points[i].rotate(pointOfRotation, angle, planeOrientation);
        }

        var rotatedBaseCenter = baseCenter.rotate(pointOfRotation, angle, planeOrientation);
        return new Pyramid(name, rotatedPoints, rotatedBaseCenter, r, height);
    }
}
