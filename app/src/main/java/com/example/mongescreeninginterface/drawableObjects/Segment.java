package com.example.mongescreeninginterface.drawableObjects;

import com.example.mongescreeninginterface.helpers.ArithmeticHelperFunctions;
import com.example.mongescreeninginterface.helpers.LineLike;
import com.example.mongescreeninginterface.helpers.Vector3d;
import com.example.mongescreeninginterface.helpers.line.LineBothScrs;
import com.example.mongescreeninginterface.helpers.line.LineFloorScr;
import com.example.mongescreeninginterface.helpers.line.LineProfileScr;
import com.example.mongescreeninginterface.helpers.point.PointBothScreenings;
import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

import java.util.Objects;

public class Segment extends LineLike<Segment> {
    public Segment(String name, Point3d startPoint, Point3d endPoint){
        super(name);
        this.firstPoint = startPoint;
        this.secondPoint = endPoint;
        this.directionVector = new Vector3d(startPoint, endPoint);

        this.floorStopper = ArithmeticHelperFunctions.findFloorStopper(this);

        this.profileStopper = ArithmeticHelperFunctions.findProfileStopper(this);
    }

    @Override
    public Segment toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        return new Segment(name, firstPoint.toMachineObject(plotCanvasViewInfo),
                secondPoint.toMachineObject(plotCanvasViewInfo));
    }
    @Override
    public LineBothScrs to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo) {
        var bothStartScrs = new PointBothScreenings(firstPoint.toMachineObject(plotCanvasViewInfo));
        var bothEndScrs = new PointBothScreenings(secondPoint.toMachineObject(plotCanvasViewInfo));

        return new LineBothScrs(
                name,
                new LineFloorScr(bothStartScrs.pointFloorScreening(), bothEndScrs.pointFloorScreening()),
                new LineProfileScr(bothStartScrs.pointProfileScreening(), bothEndScrs.pointProfileScreening())
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Segment other = (Segment) obj;
        return firstPoint.equals(other.firstPoint) && secondPoint.equals(other.secondPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPoint, secondPoint);
    }
}