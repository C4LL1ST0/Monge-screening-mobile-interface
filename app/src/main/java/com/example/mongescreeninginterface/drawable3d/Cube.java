package com.example.mongescreeninginterface.drawable3d;

import com.example.mongescreeninginterface.drawableObjects.IDrawable;
import com.example.mongescreeninginterface.drawableObjects.Line;
import com.example.mongescreeninginterface.drawableObjects.Point3d;
import com.example.mongescreeninginterface.helpers.GeometricObject;
import com.example.mongescreeninginterface.helpers.Vector3d;
import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

public class Cube extends GeometricObject implements IDrawable<Cube, Object> { // redo better
    private Point3d[] points = new Point3d[8];
    private final Line[] edges = new Line[12];
    private final Point3d center;
    private final float edgeLength;
    public Cube(String name, Point3d center, float edgeLength){
        super(name);
        this.center = center;
        this.edgeLength = edgeLength;
        initiatePoints(edgeLength);
        initiateEdges();
    }

    public Cube(String name, Point3d[] points){
        super(name);
        this.edgeLength = (float) new Vector3d(points[0], points[1]).length();
        this.center = new Point3d("S", points[0].x+edgeLength/2, points[0].y-edgeLength/2, points[0].z+edgeLength/2);
        this.points = points;
        initiateEdges();
    }

    public Point3d[] getPoints(){return points;}
    public Line[] getEdges(){return edges;}
    public Point3d getCenter(){return center;}
    public float getEdgeLengthLength() {return edgeLength;}

    private void initiatePoints(float edgeLength){
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
        edges[0] = new Line("", points[0], points[1]);
        edges[1] = new Line("", points[1], points[2]);
        edges[2] = new Line("", points[2], points[3]);
        edges[3] = new Line("", points[3], points[0]);

        edges[4] = new Line("", points[4], points[5]);
        edges[5] = new Line("", points[5], points[6]);
        edges[6] = new Line("", points[6], points[7]);
        edges[7] = new Line("", points[7], points[4]);

        edges[8] = new Line("", points[0], points[4]);
        edges[9] = new Line("", points[1], points[5]);
        edges[10] = new Line("", points[2], points[6]);
        edges[11] = new Line("", points[3], points[7]);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Cube other = (Cube)obj;
        return center.equals(other.center) &&
                Float.compare(edgeLength, other.edgeLength) == 0 &&
                points[0].equals(other.points[0]) &&
                points[7].equals(other.points[7]);

    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(center, edgeLength, points[0], points[7]);
    }

    @Override
    public Cube toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        var machinePoints = new Point3d[8];
        for (var i = 0; i<machinePoints.length; i++){
            machinePoints[i] = points[i].toMachineObject(plotCanvasViewInfo);
        }
        return new Cube(name, machinePoints);
    }

    @Override
    public Object to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo) {
        return null;
    }
}
