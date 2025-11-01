package com.example.mongescreeninginterface;

public abstract class DrawableObject<T extends  DrawableObject<T>> {
    public String name;

    public abstract T toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo);
}
