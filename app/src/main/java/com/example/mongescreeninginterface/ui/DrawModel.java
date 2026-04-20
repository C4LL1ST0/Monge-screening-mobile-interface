package com.example.mongescreeninginterface.ui;


import android.view.KeyEvent;
import android.widget.Toast;

import com.example.mongescreeninginterface.drawable3d.Cube;
import com.example.mongescreeninginterface.drawable3d.Pyramid;
import com.example.mongescreeninginterface.helpers.IDrawable;
import com.example.mongescreeninginterface.helpers.IManipulatable;
import com.example.mongescreeninginterface.helpers.IMovable;
import com.example.mongescreeninginterface.helpers.IRotable;
import com.example.mongescreeninginterface.helpers.PlaneOrientation;
import com.example.mongescreeninginterface.helpers.UserObjectAction;
import com.example.mongescreeninginterface.helpers.UserObjectActionInfo;
import com.example.mongescreeninginterface.projectableObjects.Line;
import com.example.mongescreeninginterface.projectableObjects.Point3d;
import com.example.mongescreeninginterface.projectableObjects.Segment;

import java.util.ArrayList;
import java.util.List;

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

    private UserObjectActionInfo userObjectActionInfo;

    public void toggleAction(){
        userObjectActionInfo.toggleAction();
    }

    public UserObjectAction getAction(){
        return userObjectActionInfo.getAction();
    }
    private final List<IDrawable> objectsToDraw = new ArrayList<>();

    public List<IDrawable> getObjectsToDraw() {
        return objectsToDraw;
    }

    private IManipulatable selectedObject;
    public IManipulatable getSelectedObject(){
        if(selectedObject != null){
            return selectedObject;
        }
        throw new RuntimeException("No object selected.");
    }
    public void setSelectedObject(IManipulatable selectedObject) {
        this.selectedObject = selectedObject;
    }

    private DrawModel(){
        planeOfRotation = PlaneOrientation.XY;
        userObjectActionInfo = UserObjectActionInfo.getInstance();
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

    public void updateObjectToDraw(IManipulatable oldObject, IManipulatable newObject){
        if(oldObject instanceof IDrawable oldO  && newObject instanceof IDrawable newO){
            objectsToDraw.remove(oldO);
            objectsToDraw.add(newO);
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

    public void rotateObject(IRotable<?> rotableObject, int angle){
        IRotable<?> rotated = rotableObject.rotate(rotableObject.getPointOfRotation(),
                angle, getPlaneOfRotation());
        updateObjectToDraw(rotableObject, rotated);
        setSelectedObject(rotated);
    }

    public void moveObject(IMovable<?> movableObject, float distance){
        IMovable<?> moved = movableObject.move(distance,
                getPlaneOfRotation());
        updateObjectToDraw(movableObject, moved);
        setSelectedObject(moved);
    }

    public void doUserAction(int keyCode){
        IManipulatable selectedObject;
        try {
            selectedObject = getSelectedObject();
        }catch (RuntimeException e){
            throw e;
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            switch (getAction()){
                case ROTATE:
                    if(!(selectedObject instanceof IRotable<?> rotableObject))
                        throw new IllegalArgumentException("Selected object not rotable.");
                    rotateObject(rotableObject, 5);
                    break;
                case MOVE:
                    if(!(selectedObject instanceof IMovable<?> movableObject))
                        throw new IllegalArgumentException("Selected object not movable.");
                    moveObject(movableObject, 0.2f);
                    break;
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            switch (getAction()){
                case ROTATE:
                    if(!(selectedObject instanceof IRotable<?> rotableObject))
                        throw new IllegalArgumentException("Selected object not rotable.");
                    rotateObject(rotableObject, 355);
                    break;
                case MOVE:
                    if(!(selectedObject instanceof IMovable<?> movableObject))
                        throw new IllegalArgumentException("Selected object not movable.");
                    moveObject(movableObject, -0.2f);
                    break;
            }
        }
    }
}
