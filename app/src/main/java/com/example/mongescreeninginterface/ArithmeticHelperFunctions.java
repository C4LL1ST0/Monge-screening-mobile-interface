package com.example.mongescreeninginterface;

import androidx.annotation.Nullable;

import org.ejml.data.SingularMatrixException;
import org.ejml.simple.SimpleMatrix;

public class ArithmeticHelperFunctions {

    public static final Point3d zeroPoint = new Point3d("", 0,0,0);
    public static final Plane ro = new Plane(zeroPoint, new Vector3d(1,0,0), new Vector3d(0, 0, 1));
    public static final Plane pi = new Plane(zeroPoint, new Vector3d(1,0,0), new Vector3d(0, 1, 0));
    @Nullable
    public static Point3d findIntersection(Segment line, Plane plane, String name){
        float[][] calculations = {
                {plane.firstVector.xt, plane.secondVector.xt, -line.directionVector.xt},
                {plane.firstVector.yt, plane.secondVector.yt, -line.directionVector.yt},
                {plane.firstVector.zt, plane.secondVector.zt, -line.directionVector.zt}
        };
        float[][] results = {
                {line.startPoint.x - plane.anchorPoint.x},
                {line.startPoint.y - plane.anchorPoint.y},
                {line.startPoint.z - plane.anchorPoint.z}
        };

        var a = new SimpleMatrix(calculations);
        var b = new SimpleMatrix(results);
        SimpleMatrix solved;

        try{
            solved = a.solve(b);
        } catch (SingularMatrixException e) {
            return null;
        }

        return new Point3d(name,
                (float)(line.startPoint.x + line.directionVector.xt*solved.get(2)),
                (float)(line.startPoint.y + line.directionVector.yt*solved.get(2)),
                (float)(line.startPoint.z + line.directionVector.zt*solved.get(2)));
    }

    public static Point3d findFloorStopper(Segment line, Plane plane){
        return findIntersection(line, plane, "P");
    }

    public static Point3d findProfileStopper(Segment line, Plane plane){
        return findIntersection(line, plane, "N");
    }
}
