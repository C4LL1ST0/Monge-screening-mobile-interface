package com.example.mongescreeninginterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DrawModel {
    private List<Point3d> pointsToDraw = new ArrayList<>();
    private List<Segment> segmentsToDraw = new ArrayList<>();

    public void addObjectToDraw (DrawableObject object){
        if (object instanceof Point3d) {
            pointsToDraw.add((Point3d) object);
        }else if (object instanceof Segment) {
            segmentsToDraw.add((Segment) object);
            pointsToDraw.add(((Segment) object).startPoint);
            pointsToDraw.add(((Segment) object).endPoint);

            if(((Segment) object).floorStopper != null){
                pointsToDraw.add(((Segment) object).floorStopper);
            }
            if(((Segment) object).profileStopper != null){
                pointsToDraw.add(((Segment) object).profileStopper);
            }
        }
    }

    public List<Point3d> getPointsToDraw(){return pointsToDraw;}
    public List<Segment> getSegmentsToDraw(){return segmentsToDraw;}

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
