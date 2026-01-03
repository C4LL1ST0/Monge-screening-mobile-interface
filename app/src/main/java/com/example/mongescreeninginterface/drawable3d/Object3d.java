package com.example.mongescreeninginterface.drawable3d;

import com.example.mongescreeninginterface.helpers.PlaneOrientation;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.projectableObjects.Segment;
import com.example.mongescreeninginterface.helpers.GeometricObject;

import java.util.Arrays;
import java.util.HashSet;

public class Object3d extends GeometricObject{
    protected Point3d[] points;
    protected Segment[] edges;
    protected Point3d pointOfRotation;
    public Point3d[] getPoints(){return points;}
    public Segment[] getEdges(){return edges;}
    public Point3d getPointOfRotation() {
        return pointOfRotation;
    }
    public void setPointOfRotation(Point3d pointOfRotation) {
        this.pointOfRotation = pointOfRotation;
    }

    public Object3d(String name) {
        super(name);
    }

    public Object3d(String name, Segment[] edges){
        super(name);
        this.edges = edges;

        var points = new HashSet<Point3d>();
        for(var edge : edges){
            points.add(edge.firstPoint);
            points.add(edge.secondPoint);
        }
        this.points = points.toArray(new Point3d[0]);
        this.pointOfRotation = this.points[0];
    }

    public Object3d rotate(Point3d pointOfRotation, float angle, PlaneOrientation planeOrientation){
        var rotatedEdges = new Segment[edges.length];
        for(int i = 0; i < edges.length; i++){
            rotatedEdges[i] = edges[i].rotate(pointOfRotation, angle, planeOrientation);
        }
        return new Object3d(name, rotatedEdges);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Object3d other = (Object3d) obj;

        if (this.edges == null || other.edges == null) {
            return this.edges == other.edges;
        }

        return new HashSet<>(Arrays.asList(this.edges))
                .equals(new HashSet<>(Arrays.asList(other.edges)));
    }

    @Override
    public int hashCode() {
        if (edges == null) return 0;
        return new HashSet<>(Arrays.asList(edges)).hashCode();
    }
}
