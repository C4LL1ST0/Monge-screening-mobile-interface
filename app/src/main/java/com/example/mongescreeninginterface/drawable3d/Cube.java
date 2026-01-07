package com.example.mongescreeninginterface.drawable3d;

import com.example.mongescreeninginterface.helpers.IRotable;
import com.example.mongescreeninginterface.projectableObjects.Segment;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;
import com.example.mongescreeninginterface.helpers.Vector3d;

public class Cube extends Object3d{
    private final Point3d center;
    private final float edgeLength;
    public Cube(String name, Point3d center, float edgeLength){
        super(name);
        this.center = center;
        this.edgeLength = edgeLength;
        initiatePoints(edgeLength);
        initiateEdges();

        this.pointOfRotation = center;
    }

    public Cube(String name, Point3d[] points, Point3d center){
        super(name);
        this.name = name;
        this.edgeLength = (float) new Vector3d(points[0], points[1]).length();
        this.center = center;
        this.points = points;
        initiateEdges();

        this.pointOfRotation = center;
    }
    public Point3d getCenter(){return center;}
    public float getEdgeLengthLength() {return edgeLength;}

    private void initiatePoints(float edgeLength){
        points = new Point3d[8];
        points[0] = new Point3d("A", center.x-edgeLength/2, center.y+edgeLength/2, center.z-edgeLength/2);
        points[1] = new Point3d("B", points[0].x+edgeLength, points[0].y, points[0].z);
        points[2] = new Point3d("C", points[1].x, points[1].y-edgeLength, points[1].z);
        points[3] = new Point3d("D", points[0].x, points[0].y-edgeLength, points[0].z);

        points[4] = new Point3d("E", points[0].x, points[0].y, points[0].z+edgeLength);
        points[5] = new Point3d("F", points[1].x, points[1].y, points[1].z+edgeLength);
        points[6] = new Point3d("G", points[5].x, points[5].y-edgeLength, points[5].z);
        points[7] = new Point3d("H", points[4].x, points[4].y-edgeLength, points[4].z);
    }

    private void initiateEdges(){
        edges = new Segment[12];
        edges[0] = new Segment("", points[0], points[1]);
        edges[1] = new Segment("", points[1], points[2]);
        edges[2] = new Segment("", points[2], points[3]);
        edges[3] = new Segment("", points[3], points[0]);

        edges[4] = new Segment("", points[4], points[5]);
        edges[5] = new Segment("", points[5], points[6]);
        edges[6] = new Segment("", points[6], points[7]);
        edges[7] = new Segment("", points[7], points[4]);

        edges[8] = new Segment("", points[0], points[4]);
        edges[9] = new Segment("", points[1], points[5]);
        edges[10] = new Segment("", points[2], points[6]);
        edges[11] = new Segment("", points[3], points[7]);
    }

    @Override
    public Cube rotate(Point3d pointOfRotation, float angle, PlaneOrientation planeOrientation){
        var rotatedPoints = new Point3d[8];
        for(int i = 0; i < rotatedPoints.length; i++){
            rotatedPoints[i] = points[i].rotate(pointOfRotation, angle, planeOrientation);
        }
        return new Cube(name, rotatedPoints, center);
    }
}
