package com.example.mongescreeninginterface.helpers;

import androidx.annotation.Nullable;

import com.example.mongescreeninginterface.projectableObjects.Line;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.projectableObjects.Segment;

import org.ejml.data.SingularMatrixException;
import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

public class ArithmeticHelperFunctions {

    public static final Point3d zeroPoint = new Point3d("", 0,0,0);
    public static final Plane ro = new Plane("ro", zeroPoint, new Vector3d(1,0,0), new Vector3d(0, 0, 1));
    public static final Plane pi = new Plane("pi", zeroPoint, new Vector3d(1,0,0), new Vector3d(0, 1, 0));
    @Nullable
    public static Point3d[] findIntersection(Line line, Plane plane, String name){
        float[][] calculations = {
                {plane.firstVector.xt, plane.secondVector.xt, -line.directionVector.xt},
                {plane.firstVector.yt, plane.secondVector.yt, -line.directionVector.yt},
                {plane.firstVector.zt, plane.secondVector.zt, -line.directionVector.zt}
        };
        float[][] results = {
                {line.firstPoint.x - plane.anchorPoint.x},
                {line.firstPoint.y - plane.anchorPoint.y},
                {line.firstPoint.z - plane.anchorPoint.z}
        };

        var a = new SimpleMatrix(calculations);
        var b = new SimpleMatrix(results);
        SimpleMatrix solved;

        try{
            solved = a.solve(b);
        } catch (SingularMatrixException e) {
            return null;
        }

        return new Point3d[]{new Point3d(name,
                (float) (line.firstPoint.x + line.directionVector.xt * solved.get(2)),
                (float) (line.firstPoint.y + line.directionVector.yt * solved.get(2)),
                (float) (line.firstPoint.z + line.directionVector.zt * solved.get(2)))};
    }

    @Nullable
    public static Point3d[] findIntersection(Circle c1, Circle c2, PlaneOrientation planeOrientation){
        var centerPointsDistance = new Vector3d(c2.center, c1.center).length();
        if(centerPointsDistance == 0) return null;

        var m = ((c1.radius*c1.radius - c2.radius*c2.radius) / (2*centerPointsDistance)) + (centerPointsDistance/2);
        var triangleHeight = Math.sqrt(c1.radius*c1.radius - m*m);

        float center1A, center2A, center1B, center2B; // center1A means first coordination of first circles center (not X because of plane orientation)

        if (planeOrientation == PlaneOrientation.XY){
            center1A = c1.center.x;
            center2A = c2.center.x;
            center1B = c1.center.y;
            center2B = c2.center.y;
        } else if (planeOrientation == PlaneOrientation.XZ) {
            center1A = c1.center.x;
            center2A = c2.center.x;
            center1B = c1.center.z;
            center2B = c2.center.z;
        }else {
            center1A = c1.center.y;
            center2A = c2.center.y;
            center1B = c1.center.z;
            center2B = c2.center.z;
        }

        var sx = center1A + (m/centerPointsDistance)*(center2A-center1A);
        var sy = center1B + (m/centerPointsDistance)*(center2B-center1B);

        var ca1 = sx - (triangleHeight/centerPointsDistance)*(center1B - center2B);
        var ca2 = sx + (triangleHeight/centerPointsDistance)*(center1B - center2B);
        var cb1 = sy + (triangleHeight/centerPointsDistance)*(center1A - center2A);
        var cb2 = sy - (triangleHeight/centerPointsDistance)*(center1A - center2A);

        if(Double.isNaN(ca1) || Double.isNaN(ca2) || Double.isNaN(cb1) || Double.isNaN(cb2)){
            return null;
        }

        if(planeOrientation == PlaneOrientation.XY){
            return new Point3d[]{
                    new Point3d("R1", (float) ca1, (float) cb1, c1.center.z),
                    new Point3d("R2", (float) ca2, (float) cb2, c1.center.z),
            };
        } else if (planeOrientation == PlaneOrientation.XZ) {
            return new Point3d[]{
                    new Point3d("R1", (float) ca1, c1.center.y, (float) cb1),
                    new Point3d("R2", (float) ca2, c1.center.y, (float) cb2),
            };
        }else {
            return new Point3d[]{
                    new Point3d("R1", c1.center.x, (float) ca1, (float) cb1),
                    new Point3d("R2", c1.center.x, (float) ca2, (float) cb2),
            };
        }
    }

    @Nullable
    public static Point3d findFloorStopper(Line line){
        return findIntersection(line, ArithmeticHelperFunctions.pi, "P") == null ?
                null : findIntersection(line, ArithmeticHelperFunctions.pi, "P")[0];
    }

    @Nullable
    public static Point3d findProfileStopper(Line line){
        return findIntersection(line, ArithmeticHelperFunctions.ro, "N") == null ?
                null : findIntersection(line, ArithmeticHelperFunctions.ro, "N")[0];
    }

    private static boolean pointIsInRange(Segment segment, Point3d possibleIntersection){
        var xRange = new float[]{segment.firstPoint.x, segment.secondPoint.x};
        Arrays.sort(xRange);
        var yRange = new float[]{segment.firstPoint.y, segment.secondPoint.y};
        Arrays.sort(yRange);
        var zRange = new float[]{segment.firstPoint.z, segment.secondPoint.z};
        Arrays.sort(zRange);

        return possibleIntersection.x >= xRange[0] && possibleIntersection.x <= xRange[1] &&
                possibleIntersection.y >= yRange[0] && possibleIntersection.y <= yRange[1] &&
                possibleIntersection.z >= zRange[0] && possibleIntersection.z <= zRange[1];
    }

    @Nullable
    public static Point3d findFloorStopper(Segment segment){
        var line = new Line(segment.name, segment.firstPoint, segment.secondPoint);

        if(line.floorStopper == null)
            return null;

        return pointIsInRange(segment, line.floorStopper) ? line.floorStopper : null;
    }

    @Nullable
    public static Point3d findProfileStopper(Segment segment){
        var line = new Line(segment.name, segment.firstPoint, segment.secondPoint);

        if(line.profileStopper == null)
            return null;

        return pointIsInRange(segment, line.profileStopper) ? line.profileStopper : null;
    }

    public static double cosTheoremIsosceles(double b, double angle){
        angle = (Math.PI/180)*angle;
        return Math.sqrt(b*b + b*b - (2*b*b*Math.cos(angle)));
    }
}
