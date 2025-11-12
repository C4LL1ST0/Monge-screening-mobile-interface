package com.example.mongescreeninginterface;

public interface DrawableObject<T extends  DrawableObject<T>> {
    T toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo);
}
