package com.example.mongescreeninginterface.projectableObjects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mongescreeninginterface.helpers.ArithmeticHelperFunctions;
import com.example.mongescreeninginterface.helpers.GeometricObject;
import com.example.mongescreeninginterface.helpers.Circle;
import com.example.mongescreeninginterface.helpers.IDrawable;
import com.example.mongescreeninginterface.helpers.IRotable;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;
import com.example.mongescreeninginterface.helpers.Vector2d;
import com.example.mongescreeninginterface.helpers.Vector3d;
import com.example.mongescreeninginterface.helpers.point.PointBothScreenings;
import com.example.mongescreeninginterface.helpers.point.PointFloorScreening;
import com.example.mongescreeninginterface.helpers.point.PointProfileScreening;
import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

import java.util.Objects;

public class Point3d extends GeometricObject implements IProjectable<Point3d,
        PointBothScreenings>, IDrawable, IRotable<Point3d> {
    public float x;
    public float y;
    public float z;
    protected Point3d pointOfRotation;
    public Point3d getPointOfRotation() {
        return pointOfRotation;
    }
    public void setPointOfRotation(Point3d pointOfRotation) {
        this.pointOfRotation = pointOfRotation;
    }

    public Point3d(String name, float x, float y, float z){
        super(name);
        this.x = x;
        this.y = y;
        this.z = z;

        this.pointOfRotation = ArithmeticHelperFunctions.zeroPoint;
    }

    @Override
    public Point3d toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        var machineX = plotCanvasViewInfo.centerPoint.x + plotCanvasViewInfo.getWidthSegmentSize() * this.x;
        var machineY = plotCanvasViewInfo.centerPoint.y + plotCanvasViewInfo.getHeightSegmentSize() * this.y;
        var machineZ = plotCanvasViewInfo.centerPoint.y - plotCanvasViewInfo.getHeightSegmentSize() * this.z;

        return new Point3d(name, machineX, machineY, machineZ);
    }

    @Override
    public PointBothScreenings to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo){
        var point = toMachineObject(plotCanvasViewInfo);

        var p1 = new PointFloorScreening(point.x, point.y);
        var p2 = new PointProfileScreening(point.x, point.z);

        return new PointBothScreenings(point.name, p1, p2);
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

    @Override
    public Point3d rotate(Point3d pointOfRotation, float angle, PlaneOrientation planeOrientation){
        if(lookSameOnALevel(pointOfRotation, planeOrientation)) {
            this.name = name.endsWith("'") ? name : name+"'";;
            return this;
        }

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

        var newName = name.endsWith("'") ? name : name+"'";
        if(angle > 180)
            return new Point3d(newName, rotatedPoints[0].x, rotatedPoints[0].y, rotatedPoints[0].z);
        else
           return new Point3d(newName, rotatedPoints[1].x, rotatedPoints[1].y, rotatedPoints[1].z);
    }

    public boolean lookSameOnALevel(Point3d other, PlaneOrientation planeOrientation){
        if(planeOrientation == PlaneOrientation.XY && this. x == other.x && this.y == other.y)
            return true;
        else if (planeOrientation == PlaneOrientation.XZ && this. x == other.x && this.z == other.z)
            return true;
        else if (planeOrientation == PlaneOrientation.YZ && this. y == other.y && this.z == other.z)
            return true;
        else return false;
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
                Double.compare(z, other.z) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y, z);
    }

    @Override
    public void drawSelf(Canvas canvas, PlotCanvasViewInfo plotCanvasViewInfo,
                         Paint pointPaint, Paint objectPaint) {
        var pointM = this.toMachineObject(plotCanvasViewInfo);

        canvas.drawPoint(pointM.x, pointM.y, pointPaint);
        canvas.drawPoint(pointM.x, pointM.z, pointPaint);

        if(!Objects.equals(this.name, "")){
            canvas.drawText("[" + pointM.name + "]1", pointM.x-plotCanvasViewInfo.nameOffset, pointM.y-plotCanvasViewInfo.nameOffset, pointPaint);
            canvas.drawText("[" + pointM.name + "]2", pointM.x-plotCanvasViewInfo.nameOffset, pointM.z-plotCanvasViewInfo.nameOffset, pointPaint);
        }
    }
}
