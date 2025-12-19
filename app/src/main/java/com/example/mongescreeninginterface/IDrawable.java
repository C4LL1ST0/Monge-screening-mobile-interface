package com.example.mongescreeninginterface;

public interface IDrawable<T extends IDrawable<T>> {
    T toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo);
}
