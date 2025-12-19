package com.example.mongescreeninginterface.drawableObjects;

import com.example.mongescreeninginterface.ui.PlotCanvasViewInfo;

public interface IDrawable<T, C> {
    T toMachineObject(PlotCanvasViewInfo plotCanvasViewInfo);
    C to2Screenings(PlotCanvasViewInfo plotCanvasViewInfo);
}
