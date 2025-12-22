package com.example.mongescreeninginterface.projectableObjects;

import com.example.mongescreeninginterface.helpers.ArithmeticHelperFunctions;
import com.example.mongescreeninginterface.helpers.LineLike;
import com.example.mongescreeninginterface.helpers.Vector3d;
import com.example.mongescreeninginterface.helpers.line.LineBothScrs;
import com.example.mongescreeninginterface.helpers.line.LineFloorScr;
import com.example.mongescreeninginterface.helpers.line.LineProfileScr;
import com.example.mongescreeninginterface.helpers.point.PointBothScreenings;
import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

public class Line extends LineLike<Line> {
    public Line(String name, Point3d firstPoint, Point3d secondPoint){
        super(name);
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.directionVector = new Vector3d(firstPoint, secondPoint);

        var fs = ArithmeticHelperFunctions.findFloorStopper(this);
        if(fs != null) this.floorStopper = fs;

        var ps = ArithmeticHelperFunctions.findProfileStopper(this);
        if(ps != null) this.profileStopper = ps;
    }

    @Override
    public Line toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        return new Line(name, firstPoint.toMachineObject(plotCanvasViewInfo), secondPoint.toMachineObject(plotCanvasViewInfo));
    }
    @Override
    public LineBothScrs to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo) {
        var bothStartScrs = new PointBothScreenings(firstPoint.toMachineObject(plotCanvasViewInfo));
        var bothEndScrs = new PointBothScreenings(secondPoint.toMachineObject(plotCanvasViewInfo));
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
            var bothFurthestPointScreenings = floorStopper.getFurtherPoint(firstPoint, secondPoint).to2Screenings(plotCanvasViewInfo);
            return new LineBothScrs(
                    name,
                    new LineFloorScr(bothFSScrs.pointFloorScreening(), bothFurthestPointScreenings.pointFloorScreening()),
                    new LineProfileScr(bothFSScrs.pointProfileScreening(), bothFurthestPointScreenings.pointProfileScreening())
            );

        } else if (profileStopper != null) {
            var bothFurthestPointScreenings = profileStopper.getFurtherPoint(firstPoint, secondPoint).to2Screenings(plotCanvasViewInfo);
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
        return firstPoint.equals(other.firstPoint) && secondPoint.equals(other.secondPoint) ||
                firstPoint.equals(other.secondPoint) && secondPoint.equals(other.firstPoint);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(firstPoint) + java.util.Objects.hash(secondPoint);
    }
}
