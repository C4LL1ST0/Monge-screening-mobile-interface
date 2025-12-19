package com.example.mongescreeninginterface;

import android.graphics.PointF;
import android.util.Pair;

public class Point3d extends GeometricObject implements IDrawable<Point3d> {
    public float x;
    public float y;
    public float z;

    public Point3d(String name, float x, float y, float z){
        super(name);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3d toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        var machineX = plotCanvasViewInfo.centerPoint.x + plotCanvasViewInfo.getWidthSegmentSize() * this.x;
        var machineY = plotCanvasViewInfo.centerPoint.y + plotCanvasViewInfo.getHeightSegmentSize() * this.y;
        var machineZ = plotCanvasViewInfo.centerPoint.y - plotCanvasViewInfo.getHeightSegmentSize() * this.z;

        return new Point3d(name, machineX, machineY, machineZ);
    }

    public Pair<PointF, PointF> to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo){
        var point = toMachineObject(plotCanvasViewInfo);

        var p1 = new PointF(point.x, point.y);
        var p2 = new PointF(point.x, point.z);

        return new Pair<PointF, PointF>(p1, p2);
    }

    public double distanceTo(Point3d p2){
        return new Vector3d(this, p2).length();
    }

    public Point3d getFurtherPoint(Point3d p1, Point3d p2){
        var vector1 = new Vector3d(this, p1);
        var vector2 = new Vector3d(this, p2);

        if(vector1.compareTo(vector2) > 0) return p1;
        else if (vector1.compareTo(vector2) < 0) return p2;
        else return p1;
    }

    public Point3d rotate(Point3d pointOfRotation, float angle, PlaneOrientation planeOrientation){
        Vector2d directionVector = new Vector2d(pointOfRotation, this, planeOrientation);
        var triangleBaseLength = ArithmeticHelperFunctions.cosTheoremIsosceles(directionVector.length(), angle);

        var originalX = x;
        var originalY = y;
        var originalZ = z;

        if(planeOrientation == PlaneOrientation.XY){
            this.z = pointOfRotation.z;
        } else if (planeOrientation == PlaneOrientation.XZ) {
            this.y = pointOfRotation.y;
        }else {
            this.x = pointOfRotation.x;
        }

        var c1 = new Circle("k1", pointOfRotation, directionVector.length());
        var c2 = new Circle("k2", this, triangleBaseLength);
        var rotatedPoints = ArithmeticHelperFunctions.findIntersection(c1, c2, planeOrientation);

        if(planeOrientation == PlaneOrientation.XY){
            for(Point3d p : rotatedPoints) p.z = originalZ;
            this.z = originalZ;
        } else if (planeOrientation == PlaneOrientation.XZ) {
            for(Point3d p : rotatedPoints) p.y = originalY;
            this.y = originalY;
        }else {
            for(Point3d p : rotatedPoints) p.x = originalX;
            this.z = originalZ;
        }

        return new Point3d(name+"'", rotatedPoints[1].x, rotatedPoints[1].y, rotatedPoints[1].z);
    }

    public boolean hasSameCoord(Point3d other){
        return Double.compare(x, other.x) == 0 &&
                Double.compare(y, other.y) == 0 &&
                Double.compare(z, other.z) == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Point3d other = (Point3d) obj;
        return Double.compare(x, other.x) == 0 &&
                Double.compare(y, other.y) == 0 &&
                Double.compare(z, other.z) == 0 &&
                name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, x, y, z);
    }
}
