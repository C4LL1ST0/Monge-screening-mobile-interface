package com.example.mongescreeninginterface;

public class Segment extends DrawableObject<Segment>{
    public Point3d startPoint;
    public Point3d endPoint;

    public Segment(String name, Point3d startPoint, Point3d endPoint){
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public Segment toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo) {
        return new Segment(name, startPoint.toMachineObject(plotCanvasViewInfo), endPoint.toMachineObject(plotCanvasViewInfo));
    }
}
