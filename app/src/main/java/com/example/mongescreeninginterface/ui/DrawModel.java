package com.example.mongescreeninginterface.ui;


import com.example.mongescreeninginterface.drawable3d.Cube;
import com.example.mongescreeninginterface.drawable3d.Object3d;
import com.example.mongescreeninginterface.drawable3d.Pyramid;
import com.example.mongescreeninginterface.helpers.IDrawable;
import com.example.mongescreeninginterface.helpers.IRotable;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;
import com.example.mongescreeninginterface.helpers.LineLike;
import com.example.mongescreeninginterface.projectableObjects.Line;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.projectableObjects.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DrawModel {
    private static DrawModel instance;
    public static DrawModel getInstance(){
        if(instance == null){
            instance = new DrawModel();
        }
        return instance;
    }
    public DrawModelListener drawModelListener;
    private PlaneOrientation planeOfRotation;
    public PlaneOrientation getPlaneOfRotation() {
        return planeOfRotation;
    }

    public void setPlaneOfRotation(PlaneOrientation planeOfRotation) {
        this.planeOfRotation = planeOfRotation;
    }


    private final List<IDrawable> objectsToDraw = new ArrayList<>();

    public List<IDrawable> getObjectsToDraw() {
        return objectsToDraw;
    }

    private IRotable<?> selectedObject;
    public IRotable<?> getSelectedObject(){
        if(selectedObject != null){
            return selectedObject;
        }
        throw new RuntimeException("No object selected.");
    }
    public void setSelectedObject(IDrawable selectedObject) {
        if(selectedObject instanceof IRotable<?>){
            this.selectedObject = (IRotable<?>) selectedObject;
        }
    }

    private DrawModel(){
        planeOfRotation = PlaneOrientation.XY;
    }

    public void addPoint(String name, float x, float y, float z){
        var pt = new Point3d(name, x, y, z);
        objectsToDraw.add(pt);
        setSelectedObject(pt);
    }

    public void addLineLike(String name, boolean isSegment, String firstPtName,
                            String secondPtName){
        var fp = findPointByName(firstPtName);
        var sp = findPointByName(secondPtName);
        if(fp == null || sp == null){
            throw new RuntimeException("Point not found");
        }
        var linelike = isSegment ? new Segment(name, fp, sp) : new Line(name, fp, sp);
        objectsToDraw.add(linelike);
        setSelectedObject(linelike);
    }

    public void addCube(String name, String ptName, float edgeLength){
        var centerPt = findPointByName(ptName);
        if(centerPt == null){
            throw new RuntimeException("Point not found");
        }
        var cube = new Cube(name, centerPt, edgeLength);
        objectsToDraw.add(cube);
        setSelectedObject(cube);
    }

    public void addPyramid(String name, String ptName, int ptCount, float radius, float height){
        var baseCenterPt = findPointByName(ptName);
        if(baseCenterPt == null){
            throw new RuntimeException("Point not found");
        }
        var pyramid = new Pyramid(name, ptCount, baseCenterPt, radius, height);
        objectsToDraw.add(pyramid);
        setSelectedObject(pyramid);
    }

    public void updateObjectToDraw(IRotable<?> oldObject, IRotable<?> newObject){
        if(oldObject instanceof IDrawable  && newObject instanceof IDrawable){
            objectsToDraw.remove((IDrawable) oldObject);
            objectsToDraw.add((IDrawable) newObject);
            if(drawModelListener != null)
                drawModelListener.onModelChanged();
        }
    }

    private Point3d findPointByName(String name) {
        return objectsToDraw.stream()
                .filter(o -> o instanceof Point3d)
                .map(o -> (Point3d) o)
                .filter(p -> name.equals(p.name))
                .findFirst()
                .orElse(null);
    }
//    public void squashPoints(){
//        var pointsToRemove = new ArrayList<Point3d>();
//
//        for(var pt1 : getPointsToDraw()){
//            if(pointsToRemove.contains(pt1)) continue;
//
//            var pointsWithSameCoords = new ArrayList<Point3d>();
//
//            for(var pt2 : getPointsToDraw()){
//                if(pt1.equals(pt2)) continue;
//                if(pt1.hasSameCoord(pt2)) pointsWithSameCoords.add(pt2);
//            }
//
//            if(!pointsWithSameCoords.isEmpty()){
//                pt1.name = pointsWithSameCoords.stream().map(pt -> pt.name).collect(Collectors.joining(" = ")) + "=" + pt1.name;
//            }
//
//            for(var segment : getLinesToDraw()){
//                if(segment.firstPoint.hasSameCoord(pt1)) segment.firstPoint = pt1;
//                if(segment.secondPoint.hasSameCoord(pt1)) segment.secondPoint = pt1;
//
//                if(segment.floorStopper != null){
//                    if(segment.floorStopper.hasSameCoord(pt1)) segment.floorStopper = pt1;
//                }
//                if(segment.profileStopper != null){
//                    if(segment.profileStopper.hasSameCoord(pt1)) segment.profileStopper = pt1;
//                }
//            }
//
//            pointsToRemove.addAll(pointsWithSameCoords);
//        }
//
//        pointsToDraw.removeAll(pointsToRemove);
//    }
}
