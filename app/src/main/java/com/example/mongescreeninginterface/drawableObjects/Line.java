package com.example.mongescreeninginterface.drawableObjects;

import androidx.annotation.Nullable;

import com.example.mongescreeninginterface.helpers.ArithmeticHelperFunctions;
import com.example.mongescreeninginterface.helpers.GeometricObject;
import com.example.mongescreeninginterface.helpers.Vector3d;
import com.example.mongescreeninginterface.helpers.line.LineBothScrs;
import com.example.mongescreeninginterface.helpers.line.LineFloorScr;
import com.example.mongescreeninginterface.helpers.line.LineProfileScr;
import com.example.mongescreeninginterface.helpers.point.PointBothScreenings;
import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

public class Line extends GeometricObject implements IDrawable<Line, LineBothScrs> {
    public Point3d startPoint;
    public Point3d endPoint;
    @Nullable
    public Point3d floorStopper;
    @Nullable
    public Point3d profileStopper;

    public Vector3d directionVector;

    public Line(String name, Point3d startPoint, Point3d endPoint){
        super(name);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.directionVector = new Vector3d(startPoint, endPoint);

        var fs = ArithmeticHelperFunctions.findFloorStopper(this);
        if(fs != null) this.floorStopper = fs;

        var ps = ArithmeticHelperFunctions.findProfileStopper(this);
        if(ps != null) this.profileStopper = ps;
    }

    @Override
    public Line toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        return new Line(name, startPoint.toMachineObject(plotCanvasViewInfo), endPoint.toMachineObject(plotCanvasViewInfo));
    }
    @Override
    public LineBothScrs to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo) {
        var bothStartScrs = new PointBothScreenings(startPoint.toMachineObject(plotCanvasViewInfo));
        var bothEndScrs = new PointBothScreenings(endPoint.toMachineObject(plotCanvasViewInfo));
        PointBothScreenings bothFSScrs = null;
        PointBothScreenings bothPSScrs = null;

        if(floorStopper != null)
            bothFSScrs = new PointBothScreenings(floorStopper.toMachineObject(plotCanvasViewInfo));

        if(profileStopper != null)
            bothPSScrs = new PointBothScreenings(profileStopper.toMachineObject(plotCanvasViewInfo));

        if(floorStopper != null && profileStopper != null){
            return new LineBothScrs(
                    name,
                    new LineFloorScr(bothFSScrs.pointFloorScreening(), bothPSScrs.pointFloorScreening()),
                    new LineProfileScr(bothFSScrs.pointProfileScreening(), bothPSScrs.pointProfileScreening())
            );

        }else if(floorStopper != null){
            var bothFurthestPointScreenings = floorStopper.getFurtherPoint(startPoint, endPoint).to2Screenings(plotCanvasViewInfo);
            return new LineBothScrs(
                    name,
                    new LineFloorScr(bothFSScrs.pointFloorScreening(), bothFurthestPointScreenings.pointFloorScreening()),
                    new LineProfileScr(bothFSScrs.pointProfileScreening(), bothFurthestPointScreenings.pointProfileScreening())
            );

        } else if (profileStopper != null) {
            var bothFurthestPointScreenings = profileStopper.getFurtherPoint(startPoint, endPoint).to2Screenings(plotCanvasViewInfo);
            return new LineBothScrs(
                    name,
                    new LineFloorScr(bothFurthestPointScreenings.pointFloorScreening(), bothPSScrs.pointFloorScreening()),
                    new LineProfileScr(bothFurthestPointScreenings.pointProfileScreening(), bothPSScrs.pointProfileScreening())
            );

        }else {
            return new LineBothScrs(
                    name,
                    new LineFloorScr(bothStartScrs.pointFloorScreening(), bothEndScrs.pointFloorScreening()),
                    new LineProfileScr(bothStartScrs.pointProfileScreening(), bothEndScrs.pointProfileScreening())
            );
        }

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Line other = (Line) obj;
        return startPoint.equals(other.startPoint) && endPoint.equals(other.endPoint) ||
                startPoint.equals(other.endPoint) && endPoint.equals(other.startPoint);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(startPoint) + java.util.Objects.hash(endPoint);
    }
}
