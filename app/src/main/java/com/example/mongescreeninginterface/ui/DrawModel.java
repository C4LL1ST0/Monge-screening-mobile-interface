package com.example.mongescreeninginterface.ui;

import androidx.annotation.Nullable;

import com.example.mongescreeninginterface.drawable3d.Object3d;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;
import com.example.mongescreeninginterface.projectableObjects.IProjectable;
import com.example.mongescreeninginterface.helpers.LineLike;
import com.example.mongescreeninginterface.projectableObjects.Point3d;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DrawModel {
    private static DrawModel instance;
    private final List<Point3d> pointsToDraw = new ArrayList<>();
    private final List<LineLike> linesToDraw = new ArrayList<>();
    private final List<Object3d> object3dsToDraw = new ArrayList<>();
    private Object3d selectedObject;
    public DrawModelListener drawModelListener;
    private PlaneOrientation planeOfRotation;

    private DrawModel(){
        planeOfRotation = PlaneOrientation.XY;
    }

    public static DrawModel getInstance(){
        if(instance == null){
            instance = new DrawModel();
        }
        return instance;
    }

    public void addObjectToDraw (IProjectable object){
        if (object instanceof Point3d) {
            pointsToDraw.add((Point3d) object);
        }else if (object instanceof LineLike) {
            linesToDraw.add((LineLike) object);
            pointsToDraw.add(((LineLike) object).firstPoint);
            pointsToDraw.add(((LineLike) object).secondPoint);

            if (((LineLike) object).floorStopper != null) {
                pointsToDraw.add(((LineLike) object).floorStopper);
            }
            if (((LineLike) object).profileStopper != null) {
                pointsToDraw.add(((LineLike) object).profileStopper);
            }
        }
    }

    public void addObjectToDraw (Object3d object){
        object3dsToDraw.add(object);
        if(drawModelListener != null)
            drawModelListener.onModelChanged();
    }

    public void updateObjectToDraw(Object3d oldObject, Object3d newObject){
        object3dsToDraw.remove(oldObject);
        object3dsToDraw.add(newObject);
        if(drawModelListener != null)
            drawModelListener.onModelChanged();
    }

    public List<Point3d> getPointsToDraw(){return pointsToDraw;}
    public List<LineLike> getLinesToDraw(){return linesToDraw;}
    public List<Object3d> getObject3dsToDraw(){return object3dsToDraw;}

    public PlaneOrientation getPlaneOfRotation() {
        return planeOfRotation;
    }

    public void setPlaneOfRotation(PlaneOrientation planeOfRotation) {
        this.planeOfRotation = planeOfRotation;
    }

    @Nullable
    public Object3d getSelectedObject(){
        if(!object3dsToDraw.isEmpty()){
            return object3dsToDraw.get(0);
        }
        return null;
    }
    public void setSelectedObject(Object3d selectedObject) {
        this.selectedObject = selectedObject;
    }

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

            for(var segment : getLinesToDraw()){
                if(segment.firstPoint.hasSameCoord(pt1)) segment.firstPoint = pt1;
                if(segment.secondPoint.hasSameCoord(pt1)) segment.secondPoint = pt1;

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
