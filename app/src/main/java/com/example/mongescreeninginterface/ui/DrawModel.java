package com.example.mongescreeninginterface.ui;

import com.example.mongescreeninginterface.drawable3d.Cube;
import com.example.mongescreeninginterface.drawableObjects.IDrawable;
import com.example.mongescreeninginterface.drawableObjects.Line;
import com.example.mongescreeninginterface.drawableObjects.Point3d;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DrawModel {

    private static DrawModel instance;
    private List<Point3d> pointsToDraw = new ArrayList<>();
    private List<Line> segmentsToDraw = new ArrayList<>();

    private DrawModel(){}

    public static DrawModel makeDrawmodel(){
        if(instance == null){
            instance = new DrawModel();
        }
        return instance;
    }

    public void addObjectToDraw (IDrawable object){
        if (object instanceof Point3d) {
            pointsToDraw.add((Point3d) object);
        }else if (object instanceof Line) {
            segmentsToDraw.add((Line) object);
            pointsToDraw.add(((Line) object).startPoint);
            pointsToDraw.add(((Line) object).endPoint);

            if(((Line) object).floorStopper != null){
                pointsToDraw.add(((Line) object).floorStopper);
            }
            if(((Line) object).profileStopper != null){
                pointsToDraw.add(((Line) object).profileStopper);
            }
        } else if (object instanceof Cube) {
            for (var edge : ((Cube) object).getEdges()){
                addObjectToDraw(edge);
            }
        }
    }

    public List<Point3d> getPointsToDraw(){return pointsToDraw;}
    public List<Line> getSegmentsToDraw(){return segmentsToDraw;}

    public void squashPoints(){
        var pointsToRemove = new ArrayList<Point3d>();

        for(var pt1 : getPointsToDraw()){
            if(pointsToRemove.contains(pt1)) continue;

            var pointsWithSameCoords = new ArrayList<Point3d>();

            for(var pt2 : getPointsToDraw()){
                if(pt1.equals(pt2)) continue;
                if(pt1.hasSameCoord(pt2)) pointsWithSameCoords.add(pt2);
            }

            if(!pointsWithSameCoords.isEmpty()){
                pt1.name = pointsWithSameCoords.stream().map(pt -> pt.name).collect(Collectors.joining(" = ")) + "=" + pt1.name;
            }

            for(var segment : getSegmentsToDraw()){
                if(segment.startPoint.hasSameCoord(pt1)) segment.startPoint = pt1;
                if(segment.endPoint.hasSameCoord(pt1)) segment.endPoint = pt1;

                if(segment.floorStopper != null){
                    if(segment.floorStopper.hasSameCoord(pt1)) segment.floorStopper = pt1;
                }
                if(segment.profileStopper != null){
                    if(segment.profileStopper.hasSameCoord(pt1)) segment.profileStopper = pt1;
                }
            }

            pointsToRemove.addAll(pointsWithSameCoords);
        }

        pointsToDraw.removeAll(pointsToRemove);
    }
}
