package com.example.mongescreeninginterface;

import java.util.ArrayList;
import java.util.List;

public class DrawModel {
    private List<DrawableObject> toDraw = new ArrayList<>();

    public void addObjectToDraw (DrawableObject object){
        toDraw.add(object);
    }

    public List<DrawableObject> getObjectsToDraw(){
        return toDraw;
    }
}
